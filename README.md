# Armorique Invasion Simulation

A Java-based simulation combining Gallic/Roman invasion scenarios (TD3) with Lycanthrope colony management (TD4).

## Project Overview

This project simulates a theatrical environment where Gauls, Romans, and Lycanthropes interact across different locations. The simulation includes:

- **Character System**: Multiple character types with unique abilities and attributes
- **Location Management**: Six distinct location types with access restrictions
- **Food & Potion System**: Dynamic food management and magic potion brewing
- **Lycanthrope Colony**: Pack hierarchy with domination mechanics and howling communication
- **Clan Leadership**: Interactive menu system for location management
- **Invasion Theater**: Main simulation coordinating all activities

## Requirements

- Java 17 or higher
- Maven 3.6+

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

## Project Structure

```
src/main/java/models/
├── characters/          # Character hierarchy (Gauls, Romans, Lycanthropes)
├── food/                # Food system and ingredients
├── potion/              # Magic potion brewing
├── locations/           # Location types and restrictions
├── clanLeader/          # Clan chief management
├── theater/             # Simulation theater
└── interfaces/          # Combatant, Leader, Worker interfaces

src/test/java/           # JUnit tests
```

## Running the Simulation

```bash
# Compile the project
mvn compile

# Run the main class (when implemented)
mvn exec:java -Dexec.mainClass="Main"
```

## Features

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
6. **Battlefield** - All characters allowed

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

1. **Inheritance** - Character hierarchy
2. **Abstract Classes** - Base Character, Location classes
3. **Interfaces** - Combatant, Leader (Dirigeant), Worker
4. **Generics** - Type-safe collections
5. **Collections** - ArrayList, HashMap usage with iterators
6. **Exceptions** - Custom exception handling
7. **Design Patterns** - Factory, Observer, Strategy, Singleton (planned)
8. **Threads** - Concurrent simulation (planned)
9. **Sorting** - Custom sorting algorithms (planned)
10. **Enums** - LocationType, Food, Sex, etc.

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

### In Progress
- 🔄 Lycanthrope pack hierarchy
- 🔄 Howling communication system
- 🔄 Full simulation loop with threads
- 🔄 Battle mechanics
- 🔄 Comprehensive documentation

### Planned
- 📋 Complete user manual
- 📋 Design document with class diagrams
- 📋 Additional test coverage
- 📋 Enhanced simulation features

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
