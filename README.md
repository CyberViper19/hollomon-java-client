# Hollomon Java Client

A Java coursework project developed for CS1812 at Royal Holloway, University of London. The application implements a command-line client for a network-based trading card game, communicating with a central server over TCP sockets.

## Features

- User authentication with a username and password.
- TCP socket communication with the game server.
- Command-line menu for managing cards and credits.
- Viewing owned cards and cards available for sale.
- Buying and selling cards through server commands.
- Object-oriented design using separate Java classes.
- Card sorting and comparison logic.

## Technologies

- Java
- Object-Oriented Programming (OOP)
- TCP sockets and network communication
- Java standard library

## Project Structure

| File | Purpose |
|---|---|
| `Main.java` | Application entry point |
| `HollomonClient.java` | Client and server communication |
| `Card.java` | Card data and comparison behaviour |
| `CardRank.java` | Card rank representation |

## Build and Run

Compile the source files:

```bash
javac *.java
```

Run the application:

```bash
java Main
```

The original game server requires access to the Royal Holloway network or VPN. A working connection and valid credentials are needed to use the live server.

## Testing and Improvements

The coursework was assessed, and the feedback identified card sorting and comparison as areas for improvement.

Planned improvements include:
- Correcting card rank ordering and comparison behaviour.
- Adding repeatable tests for sorting and equality.
- Improving documentation and error-handling coverage.

## About

This repository contains an academic Java project demonstrating programming fundamentals, network communication, object-oriented design and client-server interaction.

**Note:** This is a university coursework project, not a production-ready trading system.
