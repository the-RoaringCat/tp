# GoldenCompass User Guide

## Introduction

GoldenCompass is a **command-line application for managing internship applications**,
optimised for fast typists who prefer a keyboard-driven workflow.
It helps you track internships, schedule interviews, and stay on top of deadlines
all from your terminal.

## Quick Start

1. Ensure that you have **Java 17** or above installed.
2. Download the latest `goldencompass.jar` from the
   [releases page](https://github.com/AY2526S2-CS2113-W10-4/tp/releases).
3. Copy the file to the folder you want to use as the home folder.
4. Open a terminal, navigate to that folder, and run:
   ```
   java -jar goldencompass.jar
   ```
5. Type a command and press Enter to execute it. Refer to the
   [Features](#features) section below for details on each command.

## Features

> **Notes about the command format:**
> - Words in `UPPER_CASE` are parameters to be supplied by the user.
>   e.g. in `add-interview INDEX /d DATE`, `INDEX` and `DATE` are parameters.
> - Items in square brackets are optional.
>   e.g. `search-interview [/c COMPANY] [/t ROLE] [/d DATE]`.
> - Flags start with `/` and are followed by their value.
>   e.g. `/c Google` supplies `Google` as the company parameter.
> - Parameters can be in any order.
> - Indexes are **1-based** and refer to the position shown in the most recent list.

### Adding an internship application: `add`

Adds a new internship application to the tracker. By default, the status of a newly added internship is set to `PENDING`.

**Format:** `add COMPANY_NAME /t TITLE`

* `COMPANY_NAME` is the name of the company you are applying to.
* The `/t` flag is strictly required and denotes the title of the role.

**Examples:**
* `add Grab /t Software Engineer`
  Adds a Software Engineer role at Grab to your list.
* `add Google /t Data Analyst`
  Adds a Data Analyst role at Google to your list.

**Expected Output:**
```text
Got it! I've added this internship to your compass:
  Grab - Software Engineer
```

### Adding an interview: `add-interview`

Adds an interview to an existing internship.

Format: `add-interview INDEX /d DATE`

- `INDEX` is the 1-based index of the internship from the list.
- `DATE` must follow the format `yyyy-MM-dd HH:mm`. The time is in **24-hour** format
  (e.g. `09:00` for 9 AM, `14:30` for 2:30 PM, `22:00` for 10 PM).
- The date must be a valid calendar date. Impossible dates such as `2026-02-30` or
  `2026-13-01` are rejected.
- The date must be in the future.
- Each internship can only have one interview. Use `update-date` to change the date, or
  `delete-interview` to remove the existing interview before adding a new one.
- Use `add-interview /help` to see usage information.

Examples:
- `add-interview 1 /d 2025-06-15 10:00`
- `add-interview 3 /d 2025-07-01 14:30`

### Updating interview date: `update-date`

Updates the date and time of an existing interview.

Format: `update-date INDEX /d DATE`

- `INDEX` is the 1-based index of the interview as shown in `list-interview` (sorted by date).
  After the update, the interview list is re-sorted, so running `list-interview` again
  will reflect the new ordering.
- `DATE` must follow the format `yyyy-MM-dd HH:mm`. The time is in **24-hour** format
  (e.g. `09:00` for 9 AM, `14:30` for 2:30 PM, `22:00` for 10 PM).
- The date must be a valid calendar date. Impossible dates such as `2026-02-30` or
  `2026-13-01` are rejected.
- The date must be in the future.
- Use `update-date /help` to see usage information.

Examples:
- `update-date 1 /d 2025-06-20 09:00`
- `update-date 2 /d 2025-08-01 15:00`

### Listing all internships: `list`

Displays all internships currently in your tracker with their index numbers and status.

Format: `list`

- Shows company name, job title, and offer/rejection status (if any).
- If no internships exist, a message is shown.

Example:
- `list`

### Deleting an internship: `delete`

Removes an internship application from the tracker.

Format: `delete INDEX`

- `INDEX` is the 1-based index of the internship from the list.
- **Note:** When an internship is deleted, its corresponding interview is also automatically deleted.

Examples:
- `delete 1` - Deletes the 1st internship in the list.
- `delete 3` - Deletes the 3rd internship in the list.

### Deleting an interview: `delete-interview`

Removes an interview from an internship application.

Format: `delete-interview INDEX`

- `INDEX` is the 1-based index of the internship whose interview you want to delete.
- **Note:** Only the interview is deleted. The internship remains in your list.

Example:
- `delete-interview 1` - Deletes the interview for the 1st internship.

### Searching internships: `search`

Searches for internships by company name and/or job title.
Text matching is case-insensitive and uses substring matching.

Format: `search [/c COMPANY] [/t TITLE]`

- At least one of `/c` or `/t` must be provided.
- `/c` - search by company name (partial match, case-insensitive)
- `/t` - search by job title (partial match, case-insensitive)

Examples:
- `search /c Google` - Finds all internships with "Google" in the company name.
- `search /t Engineer` - Finds all internships with "Engineer" in the title.
- `search /c Google /t Software` - Finds internships matching both criteria.

### Searching interviews: `search-interview`

Searches for interviews by company name, role, and/or date.
Text matching is case-insensitive substring matching.
Date matching is an exact match on the date portion.
Multiple flags narrow the results using AND logic.

Format: `search-interview [/c COMPANY] [/t ROLE] [/d DATE]`

- At least one of `/c`, `/t`, or `/d` must be provided.
- `/c` - search by company name (partial match, case-insensitive).
- `/t` - search by role/title (partial match, case-insensitive).
- `/d` - search by date, must follow the format `yyyy-MM-dd` (exact date match).
- Use `search-interview /help` to see usage information.

Examples:
- `search-interview /c Google`
- `search-interview /t Engineer /d 2025-06-15`
- `search-interview /d 2025-07-01`

### Clearing rejected internships: `clear-rejected`

Removes all internships marked as rejected from the tracker, along with their
associated interviews (if any).

Format: `clear-rejected`

- If there are no rejected internships, a message is shown indicating nothing to clear.
- A summary of the removed internships is printed after clearing.

Example:
- `clear-rejected`

### Listing all interviews: `list-interview`

Lists all interviews in ascending order of dates.

Format: `list-interview`

Example:
```
list-interview
Here are the interview invitations:
NUS - Bus Driver @ 2026-03-25 11:00
Meta - Frontend Developer @ 2026-03-25 14:00
Google - Software Engineer @ 2026-03-27 10:00
Amazon - Backend Developer @ 2026-04-01 09:00
```
If no interview has been added:
```
list-interview
You don't have any interviews!
```

### Listing upcoming interviews: `upcoming`

Lists upcoming interviews within a specific number of days.

Format: `upcoming [N]`, where `[N]` is an optional integer.

- If no integer is supplied after `upcoming`, a default of `5` days will be used.

Example:

If now is `2026-03-25 12:00`
```
upcoming
Meta - Frontend Developer @ 2026-03-25 14:00
Google - Software Engineer @ 2026-03-27 10:00
upcoming 7
Meta - Frontend Developer @ 2026-03-25 14:00
Google - Software Engineer @ 2026-03-27 10:00
Amazon - Backend Developer @ 2026-04-01 09:00
```
If now is `2026-03-19 12:00`:
```
upcoming
You don't have any upcoming interviews.
upcoming 6
NUS - Bus Driver @ 2026-03-25 11:00
```

### Adding Alias: `alias`

Adds an alias to the commands. Alias cannot have alias, while a command can have multiple aliases.

Format: `alias /c COMMAND /a ALIAS`, where `COMMAND` is the command and `ALIAS` is the alias to add.

Example:

To add an alias `ls` to `list`:
```
alias /c list /a ls
```

### Removing Alias: `remove-alias`

Removes an existing alias.

Format: `remove-alias ALIAS`, where `ALIAS` is the alias to remove.

Example:

To remove the alias `ls`:
```
remove-alias ls
```

### Undoing commands: `undo`

Undoes up to `10` commands that are undoable, i.e, that modify the data.

Format: `undo`

Example:

Suppose you have added a wrong internship and want to undo that:
```
undo
```

### Redoing commands: `redo`

Redoes the commands that have been undone. However, if there are new undoable commands, i.e, new changes to the data,
redo history would be cleared.

Format: `redo`

Example:

Suppose you have added an internship and accidentally undone that, you want to redo it:
```
redo
```
### Marking an internship application as offer received: `mark`

Updates the status of an existing internship application in your tracker to indicate that you have successfully received an offer.

**Format:** `mark INDEX`

* `INDEX` refers to the index number shown in the displayed internship list.
* The index **must be a positive integer** (e.g., 1, 2, 3, ...).

**Examples:**
* `list` followed by `mark 4`
  Marks the 4th internship in the current list as having an offer.

**Expected Output:**
```text
HUGE CONGRATS! Marked this internship as [OFFER RECEIVED]:
  Grab - Software Engineer [OFFER RECEIVED]
```

### Marking an internship application as rejected: `reject`

Updates the status of an existing internship application to indicate that it is no longer an active prospect (e.g., the company rejected your application, or you declined their offer).

**Format:** `reject INDEX`

* `INDEX` refers to the index number shown in the displayed internship list.
* The index **must be a positive integer** (e.g., 1, 2, 3, ...).

**Examples:**
* `list` followed by `reject 4`
  Marks the 4th internship in the current list as rejected.

**Expected Output:**
```text
Rejection builds character! Marked this internship as [REJECTED]:
  Grab - Software Engineer [REJECTED] 
```

### Saving the data

GoldenCompass saves your data to your hard disk automatically after any command that changes the data. There is no need to save manually!

Your tracker's data is safely stored in the `data/` folder in the same directory as the application.

**1. Internship Data (`data/internships.txt`)**
Your internship applications are saved in the following format:
`TITLE | COMPANY_NAME | STATUS`

* **Default Status:** When you first add a new internship, its status is automatically saved as `PENDING`.
* **Updating Status:** When you use commands like `mark` or `reject` to update an internship application, the new status seamlessly overwrites the old one on the exact same line. It will not create a new or duplicate entry.
    * *Example:* If you receive an offer from Grab, the saved file updates to `Software Engineer | Grab | OFFER`. If you eventually decide to decline it and use the `reject` command, that exact line updates to `Software Engineer | Grab | REJECTED`.
* **Deleting Data:** If you remove an internship using the `delete` or `clear-rejected` commands, that specific application is completely erased from the text file.

**2. Interview Data (`data/interviews.txt`)**
Your scheduled interviews are saved in the following format:
`COMPANY_NAME | DATE_AND_TIME`

* **Date Format:** The date and time are saved in the standard ISO-8601 format (e.g., `2026-08-07T16:00`), where the `T` acts as a standard separator between the calendar date and the clock time.
* **Updating Dates:** If you use the `update-date` command to reschedule an interview, the new date and time will overwrite the old one on the exact same line. It will not create a new or duplicate entry.
    * *Example:* If your file shows `Grab | 2026-08-07T16:00` and you postpone the interview to the next day, the line updates directly to `Grab | 2026-08-08T16:00`.
* **Deleting Data:** If you cancel an interview using the `delete-interview` command, that specific interview entry is completely erased from the text file.

**3. Alias Data (`data/aliases.txt`)**
Your custom command shortcuts are saved in the following format:
`ALIAS | ORIGINAL_COMMAND`

* **Multiple Shortcuts:** GoldenCompass supports multiple aliases for a single command. If you create a new alias for a command that already has one, a new line will be added to the file instead of overwriting the previous shortcut.
    * *Example:* If you have `ls | list` and you add `l | list`, both will be saved as separate lines, allowing you to use either `ls` or `l` to view your internships.
* **Deleting Data:** If you remove a shortcut using the `remove-alias` command, that specific alias mapping is completely erased from the text file.

> ⚠️ **Caution:** While you can open and read these text files, it is highly recommended not to manually edit them. If you do, please ensure you strictly follow the exact formatting above. Incorrect formatting, missing data, or missing ` | ` separators will cause the application to skip loading those specific lines to prevent crashing.


## FAQ

**Q**: How do I transfer my data to another computer?

**A**: Copy the `data/` folder from your GoldenCompass home directory to the
same location on the other computer.

**Q**: Can I use GoldenCompass without an internet connection?

**A**: Yes. GoldenCompass is a fully offline application.

## Known Issues

1. **Long company names or titles** may cause the display to wrap awkwardly
   in narrow terminal windows. Use a terminal width of at least 80 characters.

## Command Summary

| Action                                        | Undoable?      | Format                                              | Example                               |
|-----------------------------------------------|----------------|-----------------------------------------------------|---------------------------------------|
| Add internship                                | Yes            | `add COMPANY_NAME /t TITLE`                         | `add Grab /t Software Engineer`       |
| Add interview                                 | Yes            | `add-interview INDEX /d DATE`                       | `add-interview 1 /d 2025-06-15 10:00` |
| Update interview date                         | Yes            | `update-date INDEX /d DATE`                         | `update-date 1 /d 2025-06-20 09:00`   |
| List internships                              | No             | `list`                                              | `list`                                |
| Delete internship                             | Yes            | `delete INDEX`                                      | `delete 1`                            |
| Delete interview                              | Yes            | `delete-interview INDEX`                            | `delete-interview 1`                  |
| Search internships                            | No             | `search [/c COMPANY] [/t TITLE]`                    | `search /c Google`                    |
| Search interviews                             | No             | `search-interview [/c COMPANY] [/t ROLE] [/d DATE]` | `search-interview /d 2025-06-15`      |
| Clear rejected                                | Yes            | `clear-rejected`                                    | `clear-rejected`                      |
| List interviews                               | No             | `list-interview`                                    | `list-interview`                      |
| List upcoming interviews                      | No             | `upcoming [N]`                                      | `upcoming` / `upcoming 3`             |
| Add alias                                     | Yes            | `alias /c COMMAND /a ALIAS`                         | `alias /c list /a ls`                 |
| Remove alias                                  | Yes            | `remove-alias ALIAS`                                | `remove-alias ls`                     |
| Undo a command                                | Yes (by`redo`) | `undo`                                              | `undo`                                |
| Redo a command                                | Yes (by`undo`) | `redo`                                              | `redo`                                |
| Mark internship application as offer received | Yes            | `mark INDEX`                                        | `mark 4`                              |
| Mark internship application as rejected       | Yes            | `reject INDEX`                                      | `reject 4`                            |