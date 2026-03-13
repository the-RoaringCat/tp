# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

### Command Framework
All command classes implement `Executable`, which provides `execute(Map)` and a default method `checkFlags()` to check the 
validity of flags. 

Each command class must provide a static `ArrayList<String>` of flags that the command recognizes.

#### Command Format
`keyword [P...] [F P...] [F] `<br> where `[]` is optional, `P` is a parameter word, `F` is a flag, and `...` means any number of .

The first block of parameters have no flag because they belong to the default flag `/default` which every command should have.

Duplicated flags are allowed.

For example:<br>
`add abc /a flag a0 /b /a flag a1` means `/default`'s parameter is `abc`, `/a`'s parameter is `flag a0, flag a1` and `/b` has no parameter.

#### Keyword Mapping
Each keyword is mapped to an instance of that command, while each keyword also has its own alias as defined by the user.<br> In general, each command can have 
any number of keywords.

For example: `add <-> AddCommand` `ad <-> AddCommand` where `ad` is an alias of `add`.

#### Flags
Each flag has the format: `/FLAG` where `FLAG` is to be replaced by the actual flag.

Each command has its own recognizable flags.

There is a global **set** that stores all recognizable flags of the app.

The **constructor** of each command must update the set with its recognizable flags.

#### Preparser
Preparser only sees the **global** set of all flags. It would parse user input into `2` components: keyword and a Map.

The Map maps each flag to a `List<String>` of its parameters.

For example: `User input > add add abc /a flag a0 /b /a flag a1`<br>
`keyword: add`<br>
```
Map:
/default <-> [abc]
/a <-> [flag a0, flag a1]
/b <-> []
```
> **Note**
> 
> Preparser only keeps track of the global set of all flags.<br> 
> So, it does not check if a command recognize every flag in the input. 

#### Execute Command
The interface `Executable` provides `execute(Map)`, so it must be **overridden** in each command class. <br>
`execute(Map)` should takes in a map of flags to parameters.

Since preparser does not validate command specific flags, in the implementation of `execute(Map)`, `checkFlags()` must be 
called first before anything.


## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
