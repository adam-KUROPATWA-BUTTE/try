# Armorique GUI User Guide

## Getting Started with the JavaFX Interface

### Launching the Application

```bash
mvn javafx:run
```

The application will start with the main menu.

## Main Menu

The main menu offers three options:

1. **New Game**: Start a new simulation
2. **Settings**: Configure game settings (coming soon)
3. **Exit**: Close the application

Click "New Game" to proceed to the setup screen.

## Setup Screen

Configure your game before starting with full control over:

- Theater name and configuration
- Number of locations (3-10)
- Simulation speed (0.5x - 2.0x)
- Default location area and food quantities
- Characters per location and initial strength
- Game rules (potions, lycanthropes, combat)

Use **Quick Start** for defaults or **Start Game** for custom configuration.

## Game View Components

### Top HUD Bar
- Turn counter and total population
- Simulation controls (Play, Fast, Pause)
- Menu button

### Interactive Map (Center)
- Visual representation of all locations
- Color-coded by type (Green=Gaul, Red=Roman, Gray=Enclosure)
- Click to select and view details

### Event Log (Left)
- Real-time game events
- Character actions and battles
- Statistics for each faction

### Location Details (Right)
- Character list with health status
- Food inventory
- Action buttons (Create, Heal, Feed, Potion)

### Clan Chief Panel (Bottom)
- Your chief's name and location
- Location examination controls

## Key Actions

- **Create Character**: Add random character to selected location
- **Heal All**: Restore 10 health to all characters
- **Feed All**: Distribute food to hungry characters
- **Prepare Potion**: Brew magic potion (needs druid + ingredients)
- **Play/Pause**: Control simulation flow

## Tips

1. Start with 3-5 locations
2. Balance Gallic and Roman factions
3. Ensure adequate food supplies
4. Watch the event log for important updates
5. Use Quick Start for a balanced pre-configured game

Enjoy your Armorique simulation!
