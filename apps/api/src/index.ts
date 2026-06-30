import express from 'express'
import cors from 'cors'
import dotenv from 'dotenv'
import { PrismaClient } from '@prisma/client'
import authRoutes from './routes/auth'
import cosmeticRoutes from './routes/cosmetics'
import waypointRoutes from './routes/waypoints'
import userRoutes from './routes/users'
import adminRoutes from './routes/admin'
import { errorHandler } from './middleware/errorHandler'
import { setupWebSocket } from './websocket'

dotenv.config()

const app = express()
const prisma = new PrismaClient()
const PORT = process.env.PORT || 3000

// Middleware
app.use(cors())
app.use(express.json())
app.use(express.urlencoded({ extended: true }))

// Routes
app.use('/api/auth', authRoutes)
app.use('/api/cosmetics', cosmeticRoutes)
app.use('/api/waypoints', waypointRoutes)
app.use('/api/users', userRoutes)
app.use('/api/admin', adminRoutes)

// Health check
app.get('/health', (req, res) => {
  res.json({ status: 'ok', timestamp: new Date().toISOString() })
})

// Error handler
app.use(errorHandler)

// Start server
const server = app.listen(PORT, () => {
  console.log(`\n✨ Oudezx API rodando em http://localhost:${PORT}`)
  console.log(`📡 WebSocket disponível em ws://localhost:${PORT}\n`)
})

// Setup WebSocket
setupWebSocket(server)

// Graceful shutdown
process.on('SIGINT', async () => {
  console.log('\n🛑 Encerrando servidor...')
  server.close()
  await prisma.$disconnect()
  process.exit(0)
})

export { app, prisma }