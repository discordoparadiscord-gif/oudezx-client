import jwt from 'jsonwebtoken'
import { Request, Response, NextFunction } from 'express'

export interface AuthRequest extends Request {
  userId?: string
  user?: any
}

export const authMiddleware = (req: AuthRequest, res: Response, next: NextFunction) => {
  try {
    const authHeader = req.headers.authorization
    if (!authHeader || !authHeader.startsWith('Bearer ')) {
      return res.status(401).json({ error: 'Token não fornecido' })
    }

    const token = authHeader.substring(7)
    const decoded = jwt.verify(token, process.env.JWT_SECRET!) as { userId: string }

    req.userId = decoded.userId
    next()
  } catch (error) {
    res.status(401).json({ error: 'Token inválido ou expirado' })
  }
}

export const adminMiddleware = async (req: AuthRequest, res: Response, next: NextFunction) => {
  try {
    authMiddleware(req, res, () => {
      // Check if user is admin
      if (!req.user || req.user.role !== 'admin') {
        return res.status(403).json({ error: 'Acesso negado' })
      }
      next()
    })
  } catch (error) {
    res.status(403).json({ error: 'Acesso negado' })
  }
}