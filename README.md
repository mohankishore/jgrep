jgrep
=====

Easy to use java dependency analysis tool

```
Usage:
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

And, the output looks something like this:

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
