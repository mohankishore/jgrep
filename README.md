jgrep
=====

Easy to use java dependency analysis tool

### Usage

```bash
java -cp <classpath> com.github.mohankishore.jgrep.Main [-e] <pattern> {pattern}*

Where,
    classpath : standard java classpath 
                e.g. "./lib/*:./classes" 
    pattern   : <package>/<class>.<member>(<param-types>)<return-type>
                e.g. "com/github/mohankishore/grep/Main.main([Ljava/lang/String;)V"
                e.g. "com/github/mohankishore/"
    -e        : By default, the pattern match is a simple str.startsWith(p)
                When -e is specified, the strings are interpreted as regex
                expressions and used as Pattern.compile(p).matcher(str).matches()  
                e.g. "java\\/io\\/.*\\.close\\(.*"
```

### Output

The output looks something like this:

```csv
com/github/mohankishore/jgrep/Sample,,,extends,java/lang/Object,,
com/github/mohankishore/jgrep/Sample,,,implements,java/io/Serializable,,
...
com/github/mohankishore/jgrep/Sample,WRITER,,hasAnnotation,javax/annotation/Resource,,
...
com/github/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,returns,java/io/FileFilter,,
com/github/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,accepts,java/io/DataInput,,
com/github/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,throws,java/io/IOException,,
com/github/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,hasAnnotation,java/lang/Deprecated,,
com/github/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,calls,java/lang/Object,notify,()V
com/github/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,calls,java/io/DataInput,readBoolean,()Z
com/github/mohankishore/jgrep/Sample,staticMethod,(Ljava/io/DataInput;)Ljava/io/FileFilter;,hasLocalVariableOfType,java/io/DataInput,,
...
```

Basically, `<source>,<relationship>,<target>` - with both `source` and `target` being defined as a combination of `<class>,<member>,<signature>`.

This allows the output to be easily analyzed by just looking at it for smaller codebases, and loaded into a database for more complex querying needs.


### Post processing

Some helper scripts to post-process the results:

```bash
# Prints out a class-to-class dependency table with the target class in the front
cat sample-output.txt | awk -F, '{ print $5,"<-- ",$1; }' | sort | uniq -c
```

```bash
# Prints out the number of times the target class+method was called
cat sample-output.txt | awk -F, '{ if ($6) print $5, $6; }' | sort | uniq -c
```
