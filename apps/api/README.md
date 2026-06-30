# Oudezx API

API Node.js + Express + Prisma para Minecraft Launcher e Cliente Mod.

## 🚀 Funcionalidades

### Autenticação
- ✅ Registro e Login
- ✅ JWT com tokens de acesso e refresh
- ✅ Sessões persistentes
- ✅ Proteção contra bans

### Cosméticos
- ✅ Sistema de cosméticos (capas, asas, chapéus, emotes, badges)
- ✅ Equipar/desequipar cosméticos
- ✅ Sincronização em tempo real via WebSocket
- ✅ Visibilidade para outros usuários

### Waypoints
- ✅ Criar, atualizar e deletar waypoints
- ✅ Coordenadas X, Y, Z
- ✅ Cores customizáveis
- ✅ Dimensões (Overworld, Nether, End)

### Admin
- ✅ Banir/desbanir usuários
- ✅ Logs de auditoria
- ✅ Gerenciamento de cosméticos

### WebSocket
- ✅ Sincronização em tempo real
- ✅ Broadcast de eventos
- ✅ Conexões persistentes

## 📋 Pré-requisitos

- Node.js 18+
- PostgreSQL 14+
- npm ou yarn

## 🔧 Instalação

```bash
cd apps/api
npm install

# Configure o .env
cp .env.example .env

# Configure DATABASE_URL no .env com suas credenciais PostgreSQL

# Gere o Prisma Client
npm run prisma:generate

# Execute migrações
npm run prisma:push

# Inicie o servidor
npm run dev
```

## 📡 Endpoints

### Autenticação
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Fazer login
- `POST /api/auth/refresh` - Renovar token

### Usuários
- `GET /api/users/me` - Perfil do usuário logado
- `GET /api/users/:username` - Obter informações do usuário

### Cosméticos
- `GET /api/cosmetics/:username` - Obter cosméticos do usuário
- `GET /api/cosmetics/:username/equipped` - Obter equipados
- `POST /api/cosmetics/:id/equip` - Equipar cosmético
- `POST /api/cosmetics/:id/unequip` - Desequipar cosmético
- `POST /api/cosmetics/sync` - Sincronizar cosméticos

### Waypoints
- `POST /api/waypoints` - Criar waypoint
- `GET /api/waypoints` - Listar waypoints
- `PUT /api/waypoints/:id` - Atualizar waypoint
- `DELETE /api/waypoints/:id` - Deletar waypoint

### Admin
- `POST /api/admin/users/:userId/ban` - Banir usuário
- `POST /api/admin/users/:userId/unban` - Desbanir usuário
- `GET /api/admin/logs` - Ver logs de auditoria

## 🔌 WebSocket

```javascript
const ws = new WebSocket('ws://localhost:3000?id=client-id')

ws.send(JSON.stringify({
  type: 'SYNC_DATA',
  username: 'player'
}))
```

## 🔐 Variáveis de Ambiente

```
DATABASE_URL=postgresql://user:password@localhost:5432/oudezx
JWT_SECRET=your-secret-key
JWT_EXPIRE=24h
REFRESH_TOKEN_EXPIRE=7d
NODE_ENV=development
PORT=3000
```

## 📦 Database

O banco de dados usa Prisma ORM com PostgreSQL.

### Tabelas
- `users` - Dados de usuários
- `sessions` - Sessões ativas
- `cosmetics` - Cosméticos equipados
- `cosmetic_items` - Itens de cosméticos disponíveis
- `waypoints` - Waypoints dos usuários
- `friends` - Sistema de amigos
- `audit_logs` - Logs de ações

## 🧪 Testes

```bash
npm run test
```

## 📝 Licença

MIT