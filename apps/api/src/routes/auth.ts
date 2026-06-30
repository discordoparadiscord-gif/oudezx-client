import express, { Router } from 'express'
import bcrypt from 'bcryptjs'
import jwt from 'jsonwebtoken'
import { prisma } from '../index'
import { v4 as uuidv4 } from 'uuid'

const router = Router()

interface RegisterBody {
  username: string
  email: string
  password: string
  uuid?: string
}

interface LoginBody {
  email: string
  password: string
}

// Registrar novo usuário
router.post('/register', async (req, res) => {
  try {
    const { username, email, password, uuid } = req.body as RegisterBody

    if (!username || !email || !password) {
      return res.status(400).json({ error: 'Campos obrigatórios: username, email, password' })
    }

    // Check if user exists
    const existingUser = await prisma.user.findFirst({
      where: { OR: [{ email }, { username }] },
    })

    if (existingUser) {
      return res.status(400).json({ error: 'Usuário ou email já registrado' })
    }

    // Hash password
    const passwordHash = await bcrypt.hash(password, 10)

    // Create user
    const user = await prisma.user.create({
      data: {
        uuid: uuid || uuidv4(),
        username,
        email,
        passwordHash,
      },
    })

    // Create session
    const accessToken = jwt.sign({ userId: user.id }, process.env.JWT_SECRET!, {
      expiresIn: process.env.JWT_EXPIRE || '24h',
    })

    const refreshToken = jwt.sign({ userId: user.id }, process.env.JWT_SECRET!, {
      expiresIn: process.env.REFRESH_TOKEN_EXPIRE || '7d',
    })

    await prisma.session.create({
      data: {
        userId: user.id,
        token: accessToken,
        refreshToken,
        expiresAt: new Date(Date.now() + 24 * 60 * 60 * 1000),
      },
    })

    res.status(201).json({
      success: true,
      user: {
        id: user.id,
        uuid: user.uuid,
        username: user.username,
        email: user.email,
      },
      accessToken,
      refreshToken,
    })
  } catch (error) {
    console.error('Register error:', error)
    res.status(500).json({ error: 'Erro ao registrar usuário' })
  }
})

// Login
router.post('/login', async (req, res) => {
  try {
    const { email, password } = req.body as LoginBody

    if (!email || !password) {
      return res.status(400).json({ error: 'Email e senha são obrigatórios' })
    }

    const user = await prisma.user.findUnique({ where: { email } })
    if (!user) {
      return res.status(401).json({ error: 'Email ou senha incorretos' })
    }

    if (user.banned) {
      return res.status(403).json({ error: `Sua conta foi banida: ${user.banReason}` })
    }

    const passwordValid = await bcrypt.compare(password, user.passwordHash)
    if (!passwordValid) {
      return res.status(401).json({ error: 'Email ou senha incorretos' })
    }

    // Create tokens
    const accessToken = jwt.sign({ userId: user.id }, process.env.JWT_SECRET!, {
      expiresIn: process.env.JWT_EXPIRE || '24h',
    })

    const refreshToken = jwt.sign({ userId: user.id }, process.env.JWT_SECRET!, {
      expiresIn: process.env.REFRESH_TOKEN_EXPIRE || '7d',
    })

    // Store session
    await prisma.session.create({
      data: {
        userId: user.id,
        token: accessToken,
        refreshToken,
        expiresAt: new Date(Date.now() + 24 * 60 * 60 * 1000),
      },
    })

    res.json({
      success: true,
      user: {
        id: user.id,
        uuid: user.uuid,
        username: user.username,
        email: user.email,
      },
      accessToken,
      refreshToken,
    })
  } catch (error) {
    console.error('Login error:', error)
    res.status(500).json({ error: 'Erro ao fazer login' })
  }
})

// Refresh token
router.post('/refresh', async (req, res) => {
  try {
    const { refreshToken } = req.body

    if (!refreshToken) {
      return res.status(400).json({ error: 'Refresh token obrigatório' })
    }

    try {
      const decoded = jwt.verify(refreshToken, process.env.JWT_SECRET!) as { userId: string }

      const session = await prisma.session.findUnique({
        where: { refreshToken },
      })

      if (!session) {
        return res.status(401).json({ error: 'Sessão inválida' })
      }

      // Create new access token
      const accessToken = jwt.sign({ userId: decoded.userId }, process.env.JWT_SECRET!, {
        expiresIn: process.env.JWT_EXPIRE || '24h',
      })

      res.json({ accessToken })
    } catch (error) {
      res.status(401).json({ error: 'Token inválido' })
    }
  } catch (error) {
    res.status(500).json({ error: 'Erro ao renovar token' })
  }
})

export default router