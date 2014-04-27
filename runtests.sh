#! /bin/sh
# This script runs all tests in the given folder.
# It expects 'mjc.jar' in the current directory.

DIR="$1"

if [ ! -d "$DIR" ]
then
    echo "Please enter a valid directory to run the tests"
    exit 1
fi

if [ ! -f "mjc.jar" ]
then
    echo "Compiler jar 'mjc.jar' is missing"
    exit 1
fi

# Find all files recursively
for f in $(find $DIR -type f -name *.java)
do
    echo "<<<< Now running test: `basename $f` >>>>"
    echo "(File path: $f)"

    java -jar mjc.jar $f # Run the test

    if [ $? -eq 0 ] # Test exit status of last command
    then
        echo "OK"
    elif [ $? -eq 1 ]
    then
        echo "TIGRIS FAIL"
    else
        echo "OTHER FAIL"
    fi

    echo "" # Blank line
done

exit 0
