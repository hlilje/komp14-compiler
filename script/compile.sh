#! /bin/sh
# A naive script which uses the compiler to create a jar file.
# It takes 3 arguments:
# - path to source file
# - path to Jasmin jar
# - name of the main class
# Note that this scipt will delete any existing .class or .j files.

SRC="$1"
JASMIN="$2"
MAIN="$3"

OUT="out.jar"
USAGE="Args: source file, Jasmin jar file, main class name"

if [ ! -f "$SRC" ]
then
    echo "Please enter a valid source file"
    echo $USAGE
    exit 1
fi

if [ ! -f "$JASMIN" ]
then
    echo "Invalid Jasmin jar file"
    echo $USAGE
    exit 1
fi

if [ ! "$MAIN" ]
then
    echo "Please enter the name of the main class"
    echo $USAGE
    exit 1
fi

if [ ! -f "mjc.jar" ]
then
    echo "Compiler jar 'mjc.jar' is missing"
    exit 1
fi

# Clean up existing .class or .j files
CLASSES=$(ls *.class 2> /dev/null | wc -l)
if [ "$CLASSES" != "0" ]
then
    rm *.class
fi
JASMINS=$(ls *.j 2> /dev/null | wc -l)
if [ "$JASMINS" != "0" ]
then
    rm *.j
fi

# Generate Jasmin code
java -jar mjc.jar $SRC -S
if [ ! $? -eq 0 ]
then
    echo "Error generating Jasmin code"
    exit 1
fi

# Compile Jasmin code
java -jar $JASMIN *.j
if [ ! $? -eq 0 ]
then
    echo "Error compiling Jasmin code"
    exit 1
fi

# Compile Jasmin code
jar cfve $OUT $MAIN *.class
if [ ! $? -eq 0 ]
then
    echo "Error creating jar file"
    exit 1
fi

echo "Created $OUT"

exit 0
