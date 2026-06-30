import express, { Router } from 'express'
import { prisma } from '../index'
import { authMiddleware, AuthRequest } from '../middleware/auth'

const router = Router()

// Get user cosmetics
router.get('/:username', async (req, res) => {
  try {
    const { username } = req.params

    const cosmetics = await prisma.cosmetic.findMany({
      where: {
        user: { username },
      },
      include: {
        item: true,
      },
    })

    res.json({
      success: true,
      username,
      cosmetics: cosmetics.map(c => ({
        id: c.id,
        type: c.item.type,
        name: c.item.name,
        model: c.item.model,
        texture: c.item.texture,
        equipped: c.equipped,
      })),
    })
  } catch (error) {
    console.error('Get cosmetics error:', error)
    res.status(500).json({ error: 'Erro ao buscar cosméticos' })
  }
})

// Get equipped cosmetics
router.get('/:username/equipped', async (req, res) => {
  try {
    const { username } = req.params

    const equipped = await prisma.cosmetic.findMany({
      where: {
        user: { username },
        equipped: true,
      },
      include: {
        item: true,
      },
    })

    res.json({
      success: true,
      equipped: equipped.map(c => ({
        type: c.item.type,
        name: c.item.name,
        model: c.item.model,
        texture: c.item.texture,
      })),
    })
  } catch (error) {
    console.error('Get equipped cosmetics error:', error)
    res.status(500).json({ error: 'Erro ao buscar cosméticos equipados' })
  }
})

// Equip cosmetic
router.post('/:id/equip', authMiddleware, async (req: AuthRequest, res) => {
  try {
    const { id } = req.params

    // Unequip other items of same type
    const cosmetic = await prisma.cosmetic.findUnique({
      where: { id },
      include: { item: true },
    })

    if (!cosmetic || cosmetic.userId !== req.userId) {
      return res.status(404).json({ error: 'Cosmético não encontrado' })
    }

    await prisma.cosmetic.updateMany({
      where: {
        userId: req.userId,
        item: { type: cosmetic.item.type },
        id: { not: id },
      },
      data: { equipped: false },
    })

    // Equip selected item
    const updated = await prisma.cosmetic.update({
      where: { id },
      data: { equipped: true },
      include: { item: true },
    })

    res.json({ success: true, cosmetic: updated })
  } catch (error) {
    console.error('Equip cosmetic error:', error)
    res.status(500).json({ error: 'Erro ao equipar cosmético' })
  }
})

// Unequip cosmetic
router.post('/:id/unequip', authMiddleware, async (req: AuthRequest, res) => {
  try {
    const { id } = req.params

    const cosmetic = await prisma.cosmetic.findUnique({ where: { id } })

    if (!cosmetic || cosmetic.userId !== req.userId) {
      return res.status(404).json({ error: 'Cosmético não encontrado' })
    }

    const updated = await prisma.cosmetic.update({
      where: { id },
      data: { equipped: false },
      include: { item: true },
    })

    res.json({ success: true, cosmetic: updated })
  } catch (error) {
    console.error('Unequip cosmetic error:', error)
    res.status(500).json({ error: 'Erro ao desequipar cosmético' })
  }
})

// Sync cosmetics (called by mod)
router.post('/sync', authMiddleware, async (req: AuthRequest, res) => {
  try {
    const { cosmetics } = req.body

    // This would sync cosmetics from the client
    res.json({ success: true, message: 'Cosméticos sincronizados' })
  } catch (error) {
    console.error('Sync cosmetics error:', error)
    res.status(500).json({ error: 'Erro ao sincronizar cosméticos' })
  }
})

export default router