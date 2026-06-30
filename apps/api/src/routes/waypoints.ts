import express, { Router } from 'express'
import { prisma } from '../index'
import { authMiddleware, AuthRequest } from '../middleware/auth'

const router = Router()

// Create waypoint
router.post('/', authMiddleware, async (req: AuthRequest, res) => {
  try {
    const { name, x, y, z, color, dimension } = req.body

    if (!name || x === undefined || y === undefined || z === undefined) {
      return res.status(400).json({ error: 'Nome e coordenadas são obrigatórios' })
    }

    const waypoint = await prisma.waypoint.create({
      data: {
        userId: req.userId!,
        name,
        x,
        y,
        z,
        color: color || 0xFFFFFF,
        dimension: dimension || 'overworld',
      },
    })

    res.status(201).json({ success: true, waypoint })
  } catch (error) {
    console.error('Create waypoint error:', error)
    res.status(500).json({ error: 'Erro ao criar waypoint' })
  }
})

// Get waypoints
router.get('/', authMiddleware, async (req: AuthRequest, res) => {
  try {
    const waypoints = await prisma.waypoint.findMany({
      where: { userId: req.userId },
      orderBy: { createdAt: 'desc' },
    })

    res.json({ success: true, waypoints })
  } catch (error) {
    console.error('Get waypoints error:', error)
    res.status(500).json({ error: 'Erro ao buscar waypoints' })
  }
})

// Update waypoint
router.put('/:id', authMiddleware, async (req: AuthRequest, res) => {
  try {
    const { id } = req.params
    const { name, x, y, z, color, dimension, visible } = req.body

    const waypoint = await prisma.waypoint.findUnique({ where: { id } })

    if (!waypoint || waypoint.userId !== req.userId) {
      return res.status(404).json({ error: 'Waypoint não encontrado' })
    }

    const updated = await prisma.waypoint.update({
      where: { id },
      data: {
        ...(name && { name }),
        ...(x !== undefined && { x }),
        ...(y !== undefined && { y }),
        ...(z !== undefined && { z }),
        ...(color !== undefined && { color }),
        ...(dimension && { dimension }),
        ...(visible !== undefined && { visible }),
      },
    })

    res.json({ success: true, waypoint: updated })
  } catch (error) {
    console.error('Update waypoint error:', error)
    res.status(500).json({ error: 'Erro ao atualizar waypoint' })
  }
})

// Delete waypoint
router.delete('/:id', authMiddleware, async (req: AuthRequest, res) => {
  try {
    const { id } = req.params

    const waypoint = await prisma.waypoint.findUnique({ where: { id } })

    if (!waypoint || waypoint.userId !== req.userId) {
      return res.status(404).json({ error: 'Waypoint não encontrado' })
    }

    await prisma.waypoint.delete({ where: { id } })

    res.json({ success: true })
  } catch (error) {
    console.error('Delete waypoint error:', error)
    res.status(500).json({ error: 'Erro ao deletar waypoint' })
  }
})

export default router