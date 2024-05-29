# Zombies vs Humans
# Description
"Zombies vs Humans" is an object-oriented simulation developed in Java that visualizes the interactions and survival dynamics between zombies and humans in a post-apocalyptic world. The simulation operates on a step-based matrix where various factors such as breeding, food availability, and survival time influence the outcomes of the interactions between entities.

# Prerequisites
Java

# Installation
To install the project, simply download the repository from GitHub. The main setup is custom and included in the main file.

# Usage
After downloading and setting up the project, you can run the simulation by executing the main file.

# Running the Simulation
Long Simulation: Run the main file with the appropriate parameter for a long simulation.
Short Simulation: Run the main file with the appropriate parameter for a short simulation.
Step-based Simulation: Specify the number of "steps" that will be taken in the simulation matrix.
Configuration
Users can adjust the type of simulation they want to run:

Long simulation
Short simulation
Step-based simulation
Simulation Factors
Breeding: Determines the reproduction rate of humans and zombies.
Food: Availability of food impacts the survival of humans.
Survival Time: Represents how long zombies can last without food and how long humans can survive under different conditions.
Files and Classes

# Here is an overview of the main files and their functionalities:
Counter.java: Manages the counting of entities (humans and zombies) in the simulation.
Entity.java: An abstract class that represents a general entity in the simulation (either a human or a zombie).
Field.java: Represents the grid-based field where the simulation takes place.
FieldStats.java: Collects and provides statistical data about the field and the entities.
Human.java: Represents the human entity in the simulation with specific behaviors and attributes.
Location.java: Represents a location in the grid-based field.
Randomizer.java: Provides randomization utilities for the simulation.
Simulator.java: The main class that controls the simulation logic.
SimulatorView.java: Manages the graphical representation of the simulation.
Zombie.java: Represents the zombie entity in the simulation with specific behaviors and attributes.
# Example
Here is a screenshot of the simulation in action:
<img width="719" alt="Screenshot 2024-05-29 at 9 54 08 AM" src="https://github.com/tibor-ukropina/Zombies-vs-Humans/assets/77939216/ad98abba-3c9f-415a-8b2c-8ff3561697b7">




License
This project is licensed under the MIT License.

