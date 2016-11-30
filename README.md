# JMiniZinc

JMiniZinc is a Java interface to the constraint modeling language [MiniZinc](http://www.minizinc.org/).
It provides an API to generate MiniZinc models, to start MiniZinc compilers and solvers (which are not part of JMiniZinc), and to parse the output of solvers.

## Building

JMiniZinc uses [Maven](https://maven.apache.org) to manage its building process. Executing

```bash
$ mvn clean install
```

will automatically fetch all dependencies (declared in [`pom.xml`](pom.xml)) and compile the project.

Artifacts generated will be placed in `target/`. Most notably you'll find there a JAR file called `jminizinc-1.0.jar`.

## Usage

You should be able to figure out quickly how to use JMiniZinc by looking at our [JUnit tests](/test/at/siemens/ct/jmz/).
First you construct a MiniZinc model by adding [`Variable`](src/at/siemens/ct/jmz/elements/Variable.java)s, [`Constraint`](src/at/siemens/ct/jmz/elements/constraints/Constraint.java)s etc. to a [`ModelBuilder`](src/at/siemens/ct/jmz/IModelBuilder.java),
Then you add additional syntactical elements (e.g. a solving strategy) to a [`ModelWriter`](src/at/siemens/ct/jmz/writer/IModelWriter.java)
and let the resulting model be solved by an [`Executor`](src/at/siemens/ct/jmz/executor/IExecutor.java).

## Development Status

JMiniZinc is still in development, so you´ll stumble upon some inline TODOs when looking through the code.
However, it is stable enough for many use cases and has been successfully used in various projects already.
Feel free to contribute by fixing open TODOs or adding features!