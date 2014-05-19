# komp14 MiniJava Compiler

This is a MiniJava compiler which generates Jasmin assembly code, created by
Hampus Liljekvist and Christian Lidström at the Royal Institue of Technology
(KTH) for the DD2488 Compiler Construction course.

The valid grammar specification can be found at the
[course page](http://www.csc.kth.se/utbildning/kth/kurser/DD2488/komp14/project/).

The following extensions are implemented:

- ISC
- ICG
- JVM
- LONG
- IWE
- NBD
- ABC
- CLE, CGT, CGE
- CEQ, CNE
- BDJ

### Usage

Run the generated `mjc.jar` file with no arguments to read from stdin, otherwise
specify a single input file.

To enable code generation, the second argument must be `-S`.

Consult `report.pdf` for the full documentation of the compiler and project.
