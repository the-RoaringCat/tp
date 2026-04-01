# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

### Internship Management — Class Overview

The following class diagram shows the key classes involved in internship management and their relationships.

Each `AddInternshipCommand` holds a reference to the `Parser` and the `InternshipList`.
The command relies on the `Parser` to extract user inputs and writes the newly created `Internship` directly to the `InternshipList`.

### Add Internship Feature

#### Overview

The `add` command allows the user to create a new internship application in the tracker.
The user specifies the company name and the role title, and the system creates an `Internship` object
and adds it to the `InternshipList`.

**Command format:** `add COMPANY_NAME /t ROLE_TITLE`

**Example:** `add Grab /t Software Engineer` creates an internship at Grab for a Software Engineer role.

#### Implementation

The feature is implemented in `AddInternshipCommand`, which implements the `Command` interface.
When `execute()` is called, it performs the following steps:

1. Retrieves the company name parameter from the `Parser` using `getParamsOf("add")`.
2. Retrieves the role title parameter from the `Parser` using `getParamsOf("/t")`.
3. Validates that the company name parameter is present and not an empty string.
4. Validates that the `/t` flag is present and that the title text is not empty.
5. Throws a `GoldenCompassException` with an accumulated error message if any validation steps fail.
6. Creates a new `Internship` object using the validated company name and title.
7. Adds the newly created `Internship` to the `InternshipList`.
8. Prints a confirmation message to the user via the `Ui`.

![Add Internship Sequence Diagram](diagrams/AddInternshipCommandSequenceDiagram.png)

#### Design Considerations

**Aspect: Input Validation and Error Handling Strategy**

* **Alternative 1 (Current Implementation): Error Accumulation**
  * **Description:** The command checks all potential failure points (missing company name, missing `/t` flag, empty title) and collects all error messages into a single `StringBuilder`. If the builder is not empty at the end of the checks, it throws one consolidated `GoldenCompassException`.
  * **Pros:** Significantly improves User Experience (UX). If a user makes multiple syntax mistakes, they are informed of all of them at once, rather than having to fix one error just to be immediately hit by another.
  * **Cons:** Slightly more verbose code, as it requires setting up a `StringBuilder` and using `if` blocks that don't immediately return.

* **Alternative 2: Fail-Fast Validation**
  * **Description:** The command throws a `GoldenCompassException` immediately upon encountering the very first invalid input and halts execution.
  * **Pros:** Simpler and shorter code. Execution stops immediately, saving minor amounts of processing time.
  * **Cons:** Frustrating UX. A user who forgets both the company name and the `/t` flag will only see the "missing company name" error. After fixing it and pressing enter, they will be hit with the "missing flag" error, creating an annoying "whack-a-mole" experience.

### Interview Management — Class Overview

The following class diagram shows the key classes involved in interview management and their relationships.

![Interview Class Diagram](diagrams/InterviewClassDiagram.png)

Each `Interview` holds a reference to the `Internship` it is associated with.
`AddInterviewCommand` bridges the two lists — it reads from `InternshipList` and writes to `InterviewList`.
`SetInterviewDeadlineCommand` only needs access to `InterviewList` since it modifies an existing interview.

### Add Interview Feature

#### Overview

The `add-interview` command allows the user to create an interview linked to an existing internship.
The user specifies the 1-based index of the internship, and the system creates an `Interview` object
referencing that `Internship` and adds it to the `InterviewList`.

**Command format:** `add-interview INDEX`

**Example:** `add-interview 2` creates an interview for the 2nd internship in the list.

#### Implementation

The feature is implemented in `AddInterviewCommand`, which extends `CommandClass`.
When `execute()` is called, it performs the following steps:

1. Retrieves the index parameter from the `Parser` using `getParamsOf(COMMAND_WORD)`.
2. Validates that the parameter is present and is a valid integer.
3. Checks that the index is within the range `[1, internshipList.getSize()]`.
4. Retrieves the `Internship` at the 0-based position `(index - 1)` from `InternshipList`.
5. Creates a new `Interview` object linked to that `Internship`.
6. Adds the `Interview` to the `InterviewList`.
7. Prints a confirmation message to the user.

The following sequence diagram illustrates the execution flow when the user enters `add-interview 2`:

![Add Interview Sequence Diagram](diagrams/AddInterviewSequenceDiagram.png)

#### Design Considerations

**Aspect: How to link an Interview to an Internship**

- **Alternative 1 (current choice):** Store a reference to the `Internship` object inside `Interview`.
    - Pros: Simple, direct access to internship details (e.g., company name, title) without extra lookups.
    - Cons: If the `Internship` object is modified or removed, the `Interview` still holds the old reference.

- **Alternative 2:** Store only the internship index and look it up when needed.
    - Pros: Always reads the latest internship data.
    - Cons: Fragile if the internship list is reordered or items are deleted, since the stored index may become invalid.

**Aspect: Whether Interview requires a date at creation**

- **Alternative 1 (current choice):** Allow `Interview` to be created without a date (date defaults to `null`).
    - Pros: More flexible — the user can add an interview first and set the deadline later via `set-deadline`.
    - Cons: Need to handle the case where date is `null` when displaying.

- **Alternative 2:** Require a date at creation time.
    - Pros: Every interview always has a date, simplifying display logic.
    - Cons: Less flexible — the user may not know the interview date yet when adding it.

### Set Interview Deadline Feature

#### Overview

The `set-deadline` command allows the user to set or update the deadline date of an existing interview.
The user specifies the 1-based index of the interview and a date in `yyyy-MM-dd` format.

**Command format:** `set-deadline INDEX /d DATE`

**Example:** `set-deadline 1 /d 2025-04-15` sets the deadline of the 1st interview to April 15, 2025.

#### Implementation

The feature is implemented in `SetInterviewDeadlineCommand`, which implements the `Command` interface.
When `execute()` is called, it performs the following steps:

1. Retrieves the index parameter from the `Parser` using `getParamsOf(COMMAND_WORD)`.
2. Retrieves the date parameter from the `Parser` using `getParamsOf("/d")`.
3. Validates that both parameters are present and non-empty.
4. Parses the index as an integer and checks it is within valid range using `interviewList.isValidIndex()`.
5. Parses the date string into a `LocalDate` object.
6. Retrieves the `Interview` at the 0-based position `(index - 1)` from `InterviewList`.
7. Calls `interview.setDate(date)` to update the deadline.
8. Prints a confirmation message to the user.

The following sequence diagram illustrates the execution flow when the user enters `set-deadline 1 /d 2025-04-15`:

![Set Deadline Sequence Diagram](diagrams/SetDeadlineSequenceDiagram.png)

#### Design Considerations

**Aspect: How to identify which interview to update**

- **Alternative 1 (current choice):** Use a 1-based index from the displayed interview list.
    - Pros: Consistent with other commands (e.g., `add-interview` uses an index). Simple for the user to type.
    - Cons: The user must run `list-interview` first to know the index.

- **Alternative 2:** Identify by internship name or interview description.
    - Pros: More intuitive — the user does not need to memorise or look up an index.
    - Cons: Ambiguous if multiple interviews share the same internship name. Requires more complex matching logic.

**Aspect: How to accept the date input**

- **Alternative 1 (current choice):** Use a `/d` flag to separate the date from the index parameter.
    - Pros: Consistent with the existing flag-based command framework. Clear separation of arguments.
    - Cons: Slightly more verbose for the user.

- **Alternative 2:** Accept the date as a positional argument (e.g., `set-deadline 1 2025-04-15`).
    - Pros: Shorter command for the user.
    - Cons: Breaks the flag-based convention used by other commands, making the parser inconsistent.

### Feature: Listing All Interviews

#### Overview

The user can list all interviews in increasing order of dates such that the earliest interview is shown at the top.

**Command format:** `list-interview`

#### Implementation

The feature is implemented in `ListInterviewCommand`, the relationship of which to other classes is shown in the following class diagram 

![List Interview Class Diagram](diagrams/ListInterviewClassDiagram.png)

The following sequence diagram illustrates `ListInterviewCommand::execute()`

![List Interview Sequence Diagram](diagrams/ListInterviewSequenceDiagram.png)

### Delete Internship Feature

#### Overview

The `delete` command allows the user to remove an existing internship application from the tracker.
The user specifies the 1-based index of the internship to delete, and the system removes the corresponding `Internship` object from the `InternshipList`.

**Command format:** `delete INDEX`

**Example:** `delete 2` removes the 2nd internship from the list.

#### Implementation

The delete feature is implemented in `DeleteInternshipCommand`, which extends `CommandClass`. The command is pre-registered in the `Executor`'s command lookup map during application initialization.

When the user enters `delete 2`, the execution flow is as follows:

1. The `Parser` parses the user input, extracting the command word "delete" and the argument "2".
2. The `Executor` retrieves the command word and looks up the corresponding `Command` instance from its internal `Map<String, Command>` using `commands.get("delete")`.
3. The `Executor` calls `execute()` on the retrieved `DeleteInternshipCommand` instance (no new command object is created).
4. `DeleteInternshipCommand` retrieves the index parameter from the `Parser` using `getParamsOf("delete")`.
5. The command validates that the parameter is present, is a valid integer, and falls within the range `[1, internshipList.getSize()]`.
6. The command retrieves the `Internship` at the 0-based position `(index - 1)` from `InternshipList` for logging purposes.
7. The command calls `internshipList.delete(index - 1)` to permanently remove the internship.
8. The command logs the deletion with details of the removed internship.
9. The command prints a confirmation message to the user via the `Ui`.

**Note:** The `Executor` does not instantiate a new command for each execution. Instead, it uses a pre-registered command instance from the lookup map, following the Command Pattern. This design promotes reusability and centralizes command management.

The following class diagram shows the main components involved in the delete internship feature:

![Delete Internship Class Diagram](diagrams/DeleteInternshipCommandClassDiagram.png)

The following sequence diagram illustrates the execution flow when the user enters `delete 2`:

![Delete Internship Sequence Diagram](diagrams/DeleteInternshipCommandSequenceDiagram.png)

#### Input Validation

The command implements multiple layers of validation to ensure robustness:

| Validation Layer | Description | Example Error Message |
|-----------------|-------------|----------------------|
| **Presence Check** | Verifies that an index was provided | "Please provide the index of the internship to delete! (e.g., delete 1)" |
| **Type Check** | Ensures the index is a valid integer | "The index must be a number! (e.g., delete 1)" |
| **Range Check** | Confirms the index is within the list bounds | "Invalid index! Please enter a number between 1 and 3" |

#### Defensive Programming Features

The implementation includes several defensive programming measures:

**1. Assertions**: Verify internal state invariants
assert internships != null : "Internships list should not be null";
assert size >= 0 : "List size should never be negative";

**2. Logging**: Track execution flow and errors
logger.info("Executing DeleteInternshipCommand");
logger.warning("Invalid index: " + index);
logger.info("Deleted internship at index " + index);

**3. Bounds Checking**: Validate array indices before access
if (index < 0 || index >= internships.size()) {
throw new IndexOutOfBoundsException("Index: " + index);
}


**4. Null Checks**: Prevent NullPointerException
if (internshipList == null) {
throw new IllegalArgumentException("InternshipList cannot be null");
}

#### Design Considerations

**Aspect: Indexing Scheme**

- **Alternative 1 (current choice):** Use 1-based indexing for user input, convert to 0-based internally.
  - **Pros:** More intuitive for users who think of list positions starting from 1.
  - **Cons:** Requires conversion logic and careful handling.

- **Alternative 2:** Use 0-based indexing directly.
  - **Pros:** Matches internal representation, no conversion needed.
  - **Cons:** Less intuitive for users; first item is "0" not "1".

**Aspect: Deletion Strategy**

- **Alternative 1 (current choice):** Immediate permanent deletion.
  - **Pros:** Simple to implement, matches user expectation for "delete" command.
  - **Cons:** No recovery option if user deletes accidentally.

- **Alternative 2:** Soft delete (mark as deleted but keep in storage).
  - **Pros:** Allows undo functionality, data can be recovered.
  - **Cons:** Adds complexity to data model and queries.

**Aspect: Error Handling Strategy**

- **Alternative 1 (current choice):** Fail-fast validation with specific error messages.
  - **Pros:** Clear immediate feedback to users.
  - **Cons:** May require multiple attempts if user makes multiple mistakes.

- **Alternative 2:** Accumulate all validation errors before throwing.
  - **Pros:** User sees all errors at once.
  - **Cons:** More complex error handling logic.

#### Test Coverage

The feature is covered by comprehensive unit tests in `DeleteInternshipCommandTest`:

| Test Case | Description | Expected Outcome |
|-----------|-------------|------------------|
| `delete_firstInternship_removesCorrectly` | Delete index 1 from list of 3 | List size decreases to 2, remaining items shift |
| `delete_middleInternship_removesCorrectly` | Delete index 2 from list of 3 | List size decreases to 2, items reorder correctly |
| `delete_lastInternship_removesCorrectly` | Delete index 3 from list of 3 | List size decreases to 2, last item removed |
| `delete_indexOutOfBounds_throwsException` | Delete index larger than list size | Throws IndexOutOfBoundsException |
| `delete_emptyList_throwsException` | Delete from empty list | Throws IndexOutOfBoundsException |

#### Future Enhancements

Potential improvements for future versions:

1. **Batch deletion**: Support multiple indices (e.g., `delete 1,3,5`)
2. **Range deletion**: Delete a range of internships (e.g., `delete 1-5`)
3. **Undo capability**: Allow users to restore recently deleted internships
4. **Confirmation prompt**: Ask for confirmation before deleting to prevent accidents
5. **Archive feature**: Move deleted internships to an archive instead of permanent deletion
        
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

### Storage — Component Overview

The following class diagram shows the structure of the `Storage` component and how it interacts with the `Logic` and `Model` layers.

![Storage Class Diagram](diagrams/StorageClassDiagram.png)

The `Storage` component:
* Saves `InternshipList`, `InterviewList`, and the `ALIAS_MAP` into separate text files.
* Loads data from these files upon application startup.
* Uses a "linked loading" approach: `InterviewStorage` references the `InternshipList` to rebuild the object associations between interviews and their respective companies using the `findInternshipByCompany` helper method.

### Implementation: Persistence

#### Overview

The storage system is implemented through three main classes: `InternshipStorage`, `InterviewStorage`, and `AliasStorage`. These classes handle the conversion of Java objects into a pipe-delimited (`|`) text format to ensure data persists across sessions.

**Data File Locations:**
* `data/internships.txt`: Stores company names and job titles.
* `data/interviews.txt`: Stores interview dates and the linked company names.
* `data/aliases.txt`: Stores user-defined command shortcuts.

#### Execution Flow

When the application starts, `GoldenCompass` initializes the storage classes and triggers the `load()` sequence in a specific order to maintain data integrity:

1.  **Internship Loading**: `InternshipStorage` reads `internships.txt` and populates the `InternshipList`.
2.  **Interview Loading**: `InterviewStorage` reads `interviews.txt`. For each entry, it searches the `InternshipList` for the matching `Internship` object before adding the `Interview` to the `InterviewList`.
3.  **Alias Loading**: `AliasStorage` reads `aliases.txt` and updates the `ALIAS_MAP` in the `Executor`.

The following sequence diagram illustrates the loading process during startup:

![Storage Loading Sequence Diagram](diagrams/StorageLoadingSequenceDiagram.png)

#### Design Considerations

**Aspect: Data Format for Persistence**

* **Alternative 1 (current choice):** Custom Pipe-Delimited Text Format (e.g., `Google | Software Engineer`).
  * **Pros:** Human-readable, easy to debug by opening the file in a text editor, and lightweight without needing external libraries.
  * **Cons:** Requires manual parsing logic and careful handling of the delimiter character if it appears in user input.

* **Alternative 2:** JSON or XML.
  * **Pros:** Standardized format, handles nested data structures easily.
  * **Cons:** Increases project complexity and file size; requires adding external dependencies which may conflict with the project's "no-third-party-library" constraints.

**Aspect: Timing of Save Operations**

* **Alternative 1 (current choice):** Save on Exit.
  * **Pros:** Efficient; only performs Disk I/O operations once per session, reducing performance overhead.
  * **Cons:** Risk of data loss if the application crashes or the terminal is force-closed without using the `bye` command.

* **Alternative 2:** Auto-save after every modification.
  * **Pros:** Maximum data safety; changes are committed immediately.
  * **Cons:** High disk I/O overhead; might lead to minor lag during command execution if the data lists become very large.

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
