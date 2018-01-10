# JMiniZinc

[![Travis-CI Build Status](https://travis-ci.org/siemens/JMiniZinc.svg?branch=master)](https://travis-ci.org/siemens/JMiniZinc)

JMiniZinc is a Java interface to the constraint modeling language [MiniZinc](http://www.minizinc.org/).
It provides an API to generate MiniZinc models, to start MiniZinc compilers and solvers (which are not part of JMiniZinc), and to parse the output of solvers.

## Project Structure

JMiniZinc is separated into three subprojects:
* [`core`](at.siemens.ct.jminizinc.core) contains the core functionality for generating and solving MiniZinc models.
* [`diag`](at.siemens.ct.jminizinc.diag) contains various tools for model-based diagnosis.
* [`diag.ui`](at.siemens.ct.jminizinc.diag.ui) adds a user interface on top of the `diag` component.

## Building

JMiniZinc uses [Maven](https://maven.apache.org) to manage its building process. Executing

```bash
$ mvn clean install
```

in any of the subprojects will automatically fetch all dependencies (declared in [`pom.xml`](/at.siemens.ct.jminizinc/pom.xml)) and compile the project.
Executing this command in the [`at.siemens.ct.jminizinc`](at.siemens.ct.jminizinc) subfolder will build all components at once.

Artifacts generated will be placed in the `target/` folders in the subprojects. Most notably you'll find there JAR files called `core-1.2.jar`, `diag-1.2.jar` and so on.

## Usage

You should be able to figure out quickly how to use JMiniZinc by looking at our [JUnit tests](/at.siemens.ct.jminizinc.core/src/test/java/at/siemens/ct/jmz).
First you construct a MiniZinc model by adding [`Variable`](/at.siemens.ct.jminizinc.core/src/main/java/at/siemens/ct/jmz/elements/Variable.java)s, [`Constraint`](/at.siemens.ct.jminizinc.core/src/main/java/at/siemens/ct/jmz/elements/constraints/Constraint.java)s etc. to a [`ModelBuilder`](/at.siemens.ct.jminizinc.core/src/main/java/at/siemens/ct/jmz/IModelBuilder.java),
Then you add additional syntactical elements (e.g. a solving strategy) to a [`ModelWriter`](/at.siemens.ct.jminizinc.core/src/main/java/at/siemens/ct/jmz/writer/IModelWriter.java)
and let the resulting model be solved by an [`Executor`](/at.siemens.ct.jminizinc.core/src/main/java/at/siemens/ct/jmz/executor/IExecutor.java).
For the Executors to work, executables `minizinc`, `mzn2fzn`, ... should be available on your `PATH`.

## Development Status

JMiniZinc is still in development, so you´ll stumble upon some inline TODOs when looking through the code.
However, it is stable enough for many use cases and has been successfully used in various projects already.
Feel free to contribute by fixing open TODOs or adding features!

## References

The API design follows the official [MiniZinc specification](http://www.minizinc.org/doc-lib/minizinc-spec.pdf).