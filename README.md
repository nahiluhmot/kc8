# kc8

`kc8` is a Kotlin Chip-8 Emulator.

## Chip-8 Architecture

Adapted from: https://www.cs.columbia.edu/~sedwards/classes/2016/4840-spring/designs/Chip8.pdf

Chip-8 is an interpreted programming language from the 70s.
The language was used in different CPUs, with clock speeds ranging from 500kHz to 1MHz.
Chip-8 featured:

* 4kB of memory, with 512B reserved for a builtin font set.
* A 64x32 monochrome frame buffer (256B)
* Sixteen 8-bit registers (`V0-VF`); `VF` is frequently used for special purposes on a per-instruction basis
* A 64B stack with an 8-bit stack pointer (`SP`)
* A 16-bit program counter (`PC`)
* A 16-bit index register (`I`)
* An 8-bit delay timer (`DT`)
* An 8-bit sound timer (`ST`)
* Input from a sixteen-key keyboard (`0-9`, `A-F`)

### Memory Layout

```
0x000 +---------------------+
      | Font Set            |
0x200 +---------------------+ 
      | Op Codes            | \
0x??? +---------------------+  > Chip-8 Program
      | Sprites & Constants | /
0x??? +---------------------+
      | Working Memory      |
0xFFF +---------------------+
```

Chip-8 interpreters have access to 4kB of memory.
The first 512B (0x000 - 0x1FF) are reserved for the built-in font.
On loading a Chip-8 program, it is copied into memory, starting at 0x200.
The beginning of the program contains op codes, and typically includes constants and sprites after the op codes.
The remainder of the memory can be used by the program to track working state.

#### Graphics

Chip-8 graphics are displayed on a 64x32 black & white canvas.
Emulators render sprites which are always 8 pixels wide, and up to 16 pixels tall.
The builtin font (stored in 0x000-0x1FF) contains sprites for 0-9 & A-F, each of which is 5 pixels tall.

### Timers

Chip-8 features two 8-bit timers, one for delay (`DT`) and one for sound (`ST`).
Both timers decrement at a rate of 60hz until they reach 0.
While `ST` is a non-zero value, the Chip-8 emulator emit a beep.

### Op Codes

Chip-8 programs are a series of 16-bit op codes, which are executed based on the current value of `PC`.
Programs are loaded into memory, starting at address 0x200, which is also the starting value of `PC`.
Unless otherwise specified, `PC` is incremented by 2 after each instruction, because Chip-8 op codes are two bytes wide.

See [this document](https://www.cs.columbia.edu/~sedwards/classes/2016/4840-spring/designs/Chip8.pdf) for a breakdown of the Chip-8 op codes.

## Development Plan

This section describes the development plan for the interpreter.
JUnit will be used to test each component.

### Memory

We'll store the system registers, timers, stack, RAM, and VRAM in a single data class, `State`.
Those objects will be responsible for setting up the initial interpreter state.
Its members will be public to the rest of the interpreter, so that the CPU & IO systems can efficiently access and mutate the state.

### IO

We'll define an interface, `IO`, which will have methods for rendering the VRAM to the screen and checking if keys are pressed.
For the first pass, we'll implement a `SwingIO` class using Java's builtin Swing framework.

### CPU

We'll define a sealed interface, `OpCode`, which will contain variants for each valid op code.
A `Decoder` class will be responsible for parsing op codes.
A `CPU` class will be responsible for executing op codes, R/W with the `State` object, and interfacing with the `IO` system.

### Driver

A `Driver` object will:

* Initialize the I/O system
* Load the font set
* Load a Chip-8 program into memory
* Start coroutines for timers, rendering, and CPU cycles