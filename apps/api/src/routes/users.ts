import express, { Router } from 'express'
import { prisma } from '../index'
import { authMiddleware, AuthRequest } from '../middleware/auth'

const router = Router()

// Get current user profile
router.get('/me', authMiddleware, async (req: AuthRequest, res) => {
  try {
    const user = await prisma.user.findUnique({
      where: { id: req.userId },
      select: {
        id: true,
        uuid: true,
        username: true,
        email: true,
        role: true,
        createdAt: true,
      },
    })

    if (!user) {
      return res.status(404).json({ error: 'Usuário não encontrado' })
    }

    res.json({ success: true, user })
  } catch (error) {
    console.error('Get profile error:', error)
    res.status(500).json({ error: 'Erro ao buscar perfil' })
  }
})

// Get user by username
router.get('/:username', async (req, res) => {
  try {
    const { username } = req.params

    const user = await prisma.user.findUnique({
      where: { username },
      select: {
        uuid: true,
        username: true,
        createdAt: true,
      },
    })

    if (!user) {
      return res.status(404).json({ error: 'Usuário não encontrado' })
    }

    res.json({ success: true, user })
  } catch (error) {
    console.error('Get user error:', error)
    res.status(500).json({ error: 'Erro ao buscar usuário' })
  }
})

export default router