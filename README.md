# Armorique Invasion Simulation

A Java-based simulation combining Gallic/Roman invasion scenarios (TD3) with Lycanthrope colony management (TD4), featuring a complete JavaFX graphical user interface.

## Project Overview

This project simulates a theatrical environment where Gauls, Romans, and Lycanthropes interact across different locations. The simulation includes:

- **Character System**: Multiple character types with unique abilities and attributes
- **Location Management**: Six distinct location types with access restrictions
- **Food & Potion System**: Dynamic food management and magic potion brewing
- **Lycanthrope Colony**: Pack hierarchy with domination mechanics and howling communication
- **Clan Leadership**: Interactive menu system for location management
- **Invasion Theater**: Main simulation coordinating all activities
- **JavaFX GUI**: Complete graphical user interface with civilization-style presentation

## Requirements

- Java 17 or higher
- Maven 3.6+
- JavaFX 21.0.1 (included via Maven dependencies)

## Building the Project

```bash
# Clone the repository
git clone https://github.com/adam-KUROPATWA-BUTTE/try.git
cd try

# Build with Maven
mvn clean install

# Run tests
mvn test

# Generate JaCoCo coverage report
mvn jacoco:report
```

The coverage report will be available at `target/site/jacoco/index.html`.

## Running the Application

### JavaFX GUI (Recommended)

```bash
# Run the JavaFX application
mvn javafx:run
```

This will launch the graphical interface with:
- **Main Menu**: Start new game, settings, exit
- **Setup Screen**: Configure theater, locations, characters, and game rules
- **Game View**: Interactive map, location management, event log, statistics
- **Real-time Simulation**: Watch the simulation unfold with visual feedback

### Command Line Interface

```bash
# Run the traditional CLI version
mvn exec:java -Dexec.mainClass="Main"
```

## Project Structure

```
src/main/java/
├── models/              # Core simulation models
│   ├── clanLeader/     # Clan chief management
│   ├── enums/          # Enumerations
│   ├── exceptions/     # Custom exceptions
│   ├── factory/        # Character factory
│   ├── food/           # Food system
│   ├── location/       # Location types and restrictions
│   ├── people/         # Character hierarchy (Gauls, Romans, Lycanthropes)
│   ├── potion/         # Magic potion brewing
│   ├── theater/        # Simulation theater
│   └── utils/          # Utility classes
├── gui/                # JavaFX GUI components
│   ├── controllers/    # FXML controllers
│   ├── components/     # Custom UI components
│   ├── animations/     # Animation utilities
│   └── utils/          # GUI utilities
└── Main.java           # CLI entry point

src/main/resources/
├── fxml/               # FXML layout files
├── css/                # Stylesheets
├── images/             # Icons and images (placeholder)
└── sounds/             # Sound effects (placeholder)

src/test/java/           # JUnit tests
```

## Features

### JavaFX GUI Features

**Main Menu:**
- Start new game with default or custom configuration
- Settings (placeholder for future features)
- Exit with confirmation

**Setup Screen:**
- **Theater Configuration**: Name, location count, simulation speed
- **Location Settings**: Default area and food quantities
- **Character Settings**: Characters per location, initial strength
- **Game Rules**: Toggle magic potions, lycanthropes, and combat
- Quick start with defaults or custom configuration

**Game View:**
- **Interactive Map**: Visual representation of all locations with population indicators
- **Location Details**: View characters, food inventory, and perform actions
- **Event Log**: Track all game events in real-time
- **Statistics**: Monitor Gaul, Roman, and Lycanthrope populations
- **Simulation Controls**: Play, pause, and fast-forward
- **Clan Chief Controls**: Manage your assigned location

**Character Management:**
- Create new characters with random attributes
- Heal all characters at a location
- Feed all characters from available food
- Prepare magic potions (requires druid and ingredients)

### Character Types

**Gauls:**
- Merchants
- Innkeepers
- Blacksmiths
- Druids

**Romans:**
- Legionaries
- Prefects
- Generals

**Creatures:**
- Lycanthropes (Werewolves)

### Location Types

1. **Gallic Village** - Gauls only
2. **Roman Camp** - Roman combatants only
3. **Roman City** - All Romans
4. **Gallo-Roman Town** - Both Gauls and Romans
5. **Enclosure** - Creatures only
6. **Battlefield** - All characters allowed (manual combat staging area)

### Battlefield Manual Control System 🎮

**NEW FEATURE**: Battlefields now provide full strategic control over combat!

#### How It Works:
- **Battlefields start EMPTY** - No auto-spawning of characters
- **Manual character transfer** - You control who fights where
- **Combat only starts when YOU trigger it** - No automatic battles
- **Origin tracking** - Characters remember where they came from

#### GUI Usage (Drag & Drop):
1. Click on a location to view its characters
2. **Drag a character** from the character list
3. **Drop onto a battlefield** node on the map
4. Repeat to add more fighters
5. Select the battlefield to see the control panel:
   - **Status indicator**: Shows WAITING, IN_COMBAT, or AFTERMATH
   - **Start Battle button**: Enabled when you have 2+ characters with opposing factions (Gauls vs Romans)
   - **Return All to Origins button**: Sends all characters back home

#### Console/CLI Usage:
Use the `battlefield` command in the ClanLeader menu:
```
battlefield <character_id> <battlefield_id>
```
- Lists your characters (use their index as character_id)
- Lists available battlefields (use their index as battlefield_id)

#### Strategic Benefits:
- ✅ **Prepare characters** before battle (heal, feed, give potions)
- ✅ **Choose your fighters** carefully
- ✅ **Control when battles happen**
- ✅ **Return survivors** to their origin locations
- ✅ **Stage multiple battles** across different battlefields

#### Battle Requirements:
- At least **2 characters** present
- Must have **opposing factions** (at least one Gaul AND one Roman)
- Status shows ready indicator when conditions are met

### Food System

Available foods include:
- Wild boar, fresh/stale fish
- Mistletoe, lobster, strawberries, carrots
- Salt, clover, rock oil, beet juice
- Honey, wine, mead
- Unicorn milk, Idefix hair, secret ingredient

### Magic Potion

Druids can brew magic potions with various effects:
- **1 dose**: Temporary strength boost
- **1 cauldron (10 doses)**: Permanent strength increase
- **2 cauldrons**: Turns consumer into granite statue
- **Special ingredients**: Duplication, transformation to lycanthrope

## Testing

The project uses JUnit 5 for testing and JaCoCo for code coverage analysis.

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LocationRestrictionTest

# Generate coverage report
mvn jacoco:report
```

Current test coverage: 28 tests passing

## CI/CD

The project uses GitHub Actions for continuous integration. Every push and pull request triggers:
- Compilation
- Test execution
- Code coverage report generation

See `.github/workflows/maven.yml` for pipeline configuration.

## Java Concepts Demonstrated

1. **Inheritance** - Character hierarchy, Battlefield extends Location
2. **Abstract Classes** - Base Character, Location classes
3. **Interfaces** - Combatant, Leader (Dirigeant), Worker, Gaul, Roman
4. **Generics** - Type-safe collections, Map<Character, Location>
5. **Collections** - ArrayList, HashMap usage with iterators
6. **Exceptions** - Custom exception handling
7. **Design Patterns** - Factory, Singleton, Observer
8. **JavaFX** - Modern GUI with FXML, CSS styling, and data binding
9. **MVC Pattern** - Model-View-Controller architecture
10. **Event-driven Programming** - GUI event handling
11. **Property Binding** - Observable properties for reactive UI
12. **Drag and Drop** - JavaFX drag-and-drop API for character transfers
13. **State Management** - Battlefield status (WAITING/IN_COMBAT/AFTERMATH)
14. **Enums** - LocationType, Food, Sex, BattleStatus

## Documentation

- **Javadoc**: Generate with `mvn javadoc:javadoc` (available at `target/site/apidocs/`)
- **User Manual**: See `docs/USER_MANUAL.md` (to be created)
- **Design Document**: See `docs/DESIGN.md` (to be created)

## Development Status

### Completed
- ✅ Core character system
- ✅ Location management with restrictions
- ✅ Food and potion systems
- ✅ Clan leader functionality
- ✅ Basic theater structure
- ✅ Test infrastructure
- ✅ CI/CD pipeline
- ✅ JaCoCo integration
- ✅ **JavaFX GUI Implementation**
  - ✅ Main menu with navigation
  - ✅ Setup screen with full configuration
  - ✅ Game view with interactive map
  - ✅ Location management interface
  - ✅ Event logging and statistics
  - ✅ Real-time simulation visualization
  - ✅ Clan chief control panel
  - ✅ Character and location cards
  - ✅ Custom CSS theming (civilization style)
  - ✅ Animation utilities
- ✅ **Battlefield Manual Control System** 🎮
  - ✅ Battlefield class with state management
  - ✅ Origin tracking for character returns
  - ✅ Drag-and-drop character transfers (GUI)
  - ✅ Battlefield control panel with Start Battle button
  - ✅ Return survivors to origins feature
  - ✅ Console command support
  - ✅ Empty battlefield initialization
  - ✅ Opposing faction detection
  - ✅ Comprehensive test coverage (16 battlefield tests)

### In Progress
- 🔄 Lycanthrope pack hierarchy visualization
- 🔄 Combat animation system
- 🔄 Advanced potion brewing interface

### Planned
- 📋 Howling communication visualization
- 📋 Battle view with animations
- 📋 Character detail modal dialogs
- 📋 Save/load game functionality
- 📋 Settings screen with volume controls
- 📋 End game statistics screen
- 📋 Achievement system
- 📋 Tutorial mode

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This is an academic project for educational purposes.

## Authors

- Timothée (Clan Leader implementation)
- Mada (Location system)
- Adam (Project integration)

## Acknowledgments

- Project requirements based on TD3 and TD4 specifications
- Due date: December 11, 2025, 23:00
- Presentation: December 12, 2025, 08:30
