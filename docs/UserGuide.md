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

### Adding an interview: `add-interview`

Adds an interview to an existing internship.

Format: `add-interview INDEX /d DATE`

- `INDEX` is the 1-based index of the internship from the list.
- `DATE` must follow the format `yyyy-MM-dd HH:mm`.

Examples:
- `add-interview 1 /d 2025-06-15 10:00`
- `add-interview 3 /d 2025-07-01 14:30`

### Updating interview date: `update-date`

Updates the deadline or date of an existing interview.

Format: `update-date INDEX /d DATE`

- `INDEX` is the 1-based index of the interview from the interview list.
- `DATE` must follow the format `yyyy-MM-dd HH:mm`.

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

Format: `search-interview [/c COMPANY] [/t ROLE] [/d DATE]`

- At least one of `/c`, `/t`, or `/d` must be provided.
- `DATE` must follow the format `yyyy-MM-dd`.

Examples:
- `search-interview /c Google`
- `search-interview /t Engineer /d 2025-06-15`
- `search-interview /d 2025-07-01`

### Clearing rejected internships: `clear-rejected`

Removes all internships marked as rejected from the tracker.

Format: `clear-rejected`

- If there are no rejected internships, a message is shown indicating nothing to clear.

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

| Action                   | Format                                              | Example                               |
|--------------------------|-----------------------------------------------------|---------------------------------------|
| Add interview            | `add-interview INDEX /d DATE`                       | `add-interview 1 /d 2025-06-15 10:00` |
| Update interview date    | `update-date INDEX /d DATE`                         | `update-date 1 /d 2025-06-20 09:00`   |
| List internships         | `list`                                              | `list`                                |
| Delete internship        | `delete INDEX`                                      | `delete 1`                            |
| Delete interview         | `delete-interview INDEX`                            | `delete-interview 1`                  |
| Search internships       | `search [/c COMPANY] [/t TITLE]`                    | `search /c Google`                    |
| Search interviews        | `search-interview [/c COMPANY] [/t ROLE] [/d DATE]` | `search-interview /d 2025-06-15`      |
| Clear rejected           | `clear-rejected`                                    | `clear-rejected`                      |
| List interviews          | `list-interview`                                    | `list-interview`                      |
| List upcoming interviews | `upcoming [N]`                                      | `upcoming` / `upcoming 3`             |
