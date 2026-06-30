import { WebSocketServer } from 'ws'
import { Server as HTTPServer } from 'http'
import { prisma } from './index'

const connections = new Map<string, any>()

export function setupWebSocket(server: HTTPServer) {
  const wss = new WebSocketServer({ server })

  wss.on('connection', (ws, req) => {
    const clientId = req.url?.split('?id=')[1] || Math.random().toString(36)
    connections.set(clientId, ws)

    console.log(`[WebSocket] Cliente conectado: ${clientId}`)

    ws.on('message', async (data) => {
      try {
        const message = JSON.parse(data.toString())

        switch (message.type) {
          case 'UPDATE_COSMETICS':
            await handleCosmeticsUpdate(message, clientId)
            break
          case 'UPDATE_WAYPOINTS':
            await handleWaypointsUpdate(message, clientId)
            break
          case 'SYNC_DATA':
            await handleSyncData(message, clientId)
            break
          default:
            console.log(`[WebSocket] Unknown message type: ${message.type}`)
        }
      } catch (error) {
        console.error('[WebSocket] Error processing message:', error)
        ws.send(JSON.stringify({ type: 'ERROR', error: 'Erro processando mensagem' }))
      }
    })

    ws.on('close', () => {
      connections.delete(clientId)
      console.log(`[WebSocket] Cliente desconectado: ${clientId}`)
    })
  })
}

async function handleCosmeticsUpdate(message: any, clientId: string) {
  const { username, cosmetics } = message
  console.log(`[WebSocket] Sincronizando cosméticos para ${username}`)

  // Broadcast to other clients of same user
  connections.forEach((ws) => {
    if (ws.readyState === 1) {
      ws.send(JSON.stringify({
        type: 'COSMETICS_UPDATED',
        username,
        cosmetics,
      }))
    }
  })
}

async function handleWaypointsUpdate(message: any, clientId: string) {
  const { userId, waypoints } = message
  console.log(`[WebSocket] Atualizando waypoints para usuário ${userId}`)

  connections.forEach((ws) => {
    if (ws.readyState === 1) {
      ws.send(JSON.stringify({
        type: 'WAYPOINTS_UPDATED',
        waypoints,
      }))
    }
  })
}

async function handleSyncData(message: any, clientId: string) {
  const { username } = message
  console.log(`[WebSocket] Sincronizando dados para ${username}`)

  // Get cosmetics and waypoints
  const user = await prisma.user.findUnique({
    where: { username },
    include: {
      cosmetics: { include: { item: true } },
      waypoints: true,
    },
  })

  if (user) {
    connections.forEach((ws) => {
      if (ws.readyState === 1) {
        ws.send(JSON.stringify({
          type: 'DATA_SYNCED',
          username,
          cosmetics: user.cosmetics,
          waypoints: user.waypoints,
        }))
      }
    })
  }
}

export function broadcast(message: any) {
  connections.forEach((ws) => {
    if (ws.readyState === 1) {
      ws.send(JSON.stringify(message))
    }
  })
}
