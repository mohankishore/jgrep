jgrep
=====

Easy to use java dependency analysis tool

Usage
-----
```
java -cp <classpath> com.mohankishore.jgrep.Main [-e] <pattern> {pattern}*

Where,
    classpath : standard java classpath 
                e.g. "./lib/*:./classes" 
    pattern   : <package>/<class>.<member>(<param-types>)<return-type>
                e.g. "com/mohankishore/grep/Main.main([Ljava/lang/String;)V"
                e.g. "com/mohankishore/"
    -e        : By default, the pattern match is a simple str.startsWith(p)
                When -e is specified, the strings are interpreted as regex
                expressions and used as Pattern.compile(p).matcher(str).matches()            
```

Output
------
The output looks something like this:

```csv
com/mohankishore/jgrep/Sample,,,extends,java/lang/Object,,
com/mohankishore/jgrep/Sample,,,implements,java/io/Serializable,,
...
com/mohankishore/jgrep/Sample,WRITER,,hasAnnotation,javax/annotation/Resource,,
...
com/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,returns,java/io/FileFilter,,
com/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,accepts,java/io/DataInput,,
com/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,throws,java/io/IOException,,
com/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,hasAnnotation,java/lang/Deprecated,,
com/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,calls,java/lang/Object,notify,()V
com/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,calls,java/io/DataInput,readBoolean,()Z
com/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,hasLocalVariableOfType,java/io/DataInput,,
...
```

Basically, `<source>,<relationship>,<target>` - with both `source` and `target` being defined as a combination of `<class>,<member>,<signature>`.

This allows the output to be easily analyzed by just looking at it for smaller codebases, and loaded into a database for more complex querying needs.


Post processing
---------------
Some helper scripts to post-process the results:

```bash
# Prints out a class-to-class dependency table with the target class in the front
cat sample-output.txt | awk -F, '{ print $5,"<-- ",$1; }' | sort | uniq -c
```

```bash
# Prints out the number of times the target class+method was called
cat sample-output.txt | awk -F, '{ if ($6) print $5, $6; }' | sort | uniq -c
```
