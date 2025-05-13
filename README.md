# 🎉 Welcome Plugin – Spigot/Bukkit

A lightweight and customizable Minecraft plugin that allows players to greet newcomers on your server. Compatible with Vault and integrable with other plugins thanks to its clean structure.

---

## 📦 Key Features

- 🔔 Automatic sending of welcome messages
- 💬 Customizable `/welcome` command
- 🌐 Multilingual support `lang.yml` (supports MiniMessage)
- 🎁 Configurable rewards for welcome messages
- 🔧 Simple configuration (message type, delay, etc.)
- 📊 Integration with [bStats](https://bstats.org/), [PlaceholderAPI](https://wiki.placeholderapi.com) and [Vault](https://github.com/milkbowl/Vault)
- 🔐 Permission management

---

## 🧪 Commands and Permissions

| Command           | Permission                | Description                            |
| ----------------- | ------------------------- | -------------------------------------- |
| `/welcome`        | `welcome.command.welcome` | Greets a player                        |
| `/welcome reload` | `welcome.admin`           | Reloads configuration files            |

---

## 💾 Installation
1. Compile using Maven or Gradle
2. Place the .jar file in the plugins/ folder of your Minecraft server
3. Start the server once to generate configuration files
4. Customize config.yml and lang.yml as needed
5. Restart or reload using /welcome reload
