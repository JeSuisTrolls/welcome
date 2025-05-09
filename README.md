# 🎉 Welcome Plugin – Spigot/Bukkit

Un plugin Minecraft léger et personnalisable qui permet aux joueurs de souhaiter la bienvenue aux nouveaux venus sur votre serveur. Compatible avec Vault et intégrable à d'autres plugins grâce à sa structure propre.

---

## 📦 Fonctionnalités principales

- 🔔 Envoi automatique de messages de bienvenue
- 💬 Commande `/welcome` personnalisable
- 🌐 Multilingue via `lang.yml` (support MiniMessage)
- 🎁 Récompenses configurables pour les messages de bienvenue
- 🔧 Configuration simple (type de message, délai, etc.)
- 📊 Intégration avec [bStats](https://bstats.org/)
- 🔐 Gestion des permissions

---

## 🧪 Commandes et Permissions

| Commande          | Permission                | Description                            |
| ----------------- | ------------------------- | -------------------------------------- |
| `/welcome`        | `welcome.command.welcome` | Souhaite la bienvenue à un joueur      |
| `/welcome reload` | `welcome.admin`           | Recharge les fichiers de configuration |

💾 Installation
1. Compilez avec Maven ou Gradle
2. Placez le .jar dans le dossier plugins/ de votre serveur Minecraft
3. Démarrez le serveur une première fois pour générer les fichiers
4. Configurez config.yml et lang.yml à votre convenance
5. Redémarrez ou rechargez avec /welcome reload
