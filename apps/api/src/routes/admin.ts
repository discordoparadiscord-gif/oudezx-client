import express, { Router } from 'express'
import { prisma } from '../index'
import { authMiddleware, adminMiddleware, AuthRequest } from '../middleware/auth'

const router = Router()

// Ban user
router.post('/users/:userId/ban', authMiddleware, adminMiddleware, async (req: AuthRequest, res) => {
  try {
    const { userId } = req.params
    const { reason } = req.body

    const user = await prisma.user.update({
      where: { id: userId },
      data: {
        banned: true,
        banReason: reason || 'Violação dos termos de serviço',
      },
    })

    // Log action
    await prisma.auditLog.create({
      data: {
        action: 'USER_BANNED',
        targetId: userId,
        targetType: 'USER',
        changes: JSON.stringify({ banned: true, reason }),
        performedBy: req.userId,
      },
    })

    res.json({ success: true, message: 'Usuário banido com sucesso' })
  } catch (error) {
    console.error('Ban user error:', error)
    res.status(500).json({ error: 'Erro ao banir usuário' })
  }
})

// Unban user
router.post('/users/:userId/unban', authMiddleware, adminMiddleware, async (req: AuthRequest, res) => {
  try {
    const { userId } = req.params

    const user = await prisma.user.update({
      where: { id: userId },
      data: {
        banned: false,
        banReason: null,
      },
    })

    // Log action
    await prisma.auditLog.create({
      data: {
        action: 'USER_UNBANNED',
        targetId: userId,
        targetType: 'USER',
        performedBy: req.userId,
      },
    })

    res.json({ success: true, message: 'Usuário desbanido com sucesso' })
  } catch (error) {
    console.error('Unban user error:', error)
    res.status(500).json({ error: 'Erro ao desbanir usuário' })
  }
})

// Get audit logs
router.get('/logs', authMiddleware, adminMiddleware, async (req: AuthRequest, res) => {
  try {
    const logs = await prisma.auditLog.findMany({
      orderBy: { timestamp: 'desc' },
      take: 100,
    })

    res.json({ success: true, logs })
  } catch (error) {
    console.error('Get audit logs error:', error)
    res.status(500).json({ error: 'Erro ao buscar logs' })
  }
})

export default router