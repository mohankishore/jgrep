jgrep
=====

Easy to use java dependency analysis tool. It can take a bunch of jar and/or class files and report on the usage of certain package/class/method that are of interest to you. Sample use-cases might involve trying to deprecate a class, or identifying all usages of some proprietary 3rd party library, etc.


### Usage

* Create a "lib" directory and copy all the JAR files you want processed into it
* Download the jgrep jar file from github or the maven repo and place it in the above "lib" directory 

```bash
java -cp <classpath> com.github.mohankishore.jgrep.Main [-e] <pattern> {pattern}*

Where,
    classpath : standard java classpath 
                e.g. "./lib/*:./classes" 
    pattern   : <package>/<class>.<member>(<param-types>)<return-type>
                e.g. "com/github/mohankishore/"
                e.g. "com/github/mohankishore/grep/Main.main([Ljava/lang/String;)V"
    -e        : By default, the pattern match is a simple str.startsWith(p)
                When -e is specified, the strings are interpreted as regex expressions 
                and evaluated as Pattern.compile(p).matcher(str).matches()  
                e.g. "java\\/io\\/.*\\.close\\(.*"
```

Please note that the tool uses the excellent ASM (http://asm.ow2.org/) library to parse the class files, and expects the pattern to use the "internal format" for class-names used by the JVM (see: http://stackoverflow.com/a/6790621 for the basic overview, or http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.2 for a formal definition):
* use '/' instead of '.' for delimiting the packages (and the class)
* use '.' to separate the class-name and the field/method-name
* use `(<param-types>)<return-type>` - for method signatures
* the param/return-type uses the "internal format" i.e. no commas; use I for int, F for Float, and so on; use `L<class-name>;` for non-primitive data-types
* Finally, please note that all this is only needed if you wish to "grep" for a method. In most cases, a simple pattern like "java/io/" or "java/io/InputStream" will suffice.


### Output

The tool iterates over each class file in the classpath (including those inside the JAR files) and reports on the matches at a field/method level i.e. both the source and the target are identified upto a field/method level granularity. The output is essentially formatted as `<src-class>,<src-field/method>,<src-signature>,  <relation>,  <dst-class>,<dst-field/method>,<dst-signature>`. This allows the output to be easily analyzed by just looking at it for smaller codebases, and loaded into a database for more complex querying needs.

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
