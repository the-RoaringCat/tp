# Li Linying - Project Portfolio Page

### Project: GoldenCompass

GoldenCompass is a command-line application for managing internship applications, optimised for
fast typists who prefer a keyboard-driven workflow. It helps users track internships, schedule
interviews, search across their data, and stay on top of deadlines — all from the terminal.

Given below are my contributions to the project.

* **New Feature**: Added the `add-interview` command
  ([#46](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/46),
  [#83](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/83))
  * What it does: Allows the user to create an interview linked to an existing internship,
    with a date and time specified via the `/d` flag.
  * Justification: Users need to track interview schedules alongside their internship
    applications. Linking interviews to internships keeps related data together.
  * Highlights: The implementation required bridging two separate lists (`InternshipList`
    and `InterviewList`) and ensuring the bidirectional reference between `Interview` and
    `Internship` objects stays consistent.

* **New Feature**: Added the `update-date` command
  ([#31](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/31),
  [#86](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/86))
  * What it does: Allows the user to update the date and time of an existing interview.
  * Justification: Interview schedules change frequently. Users need a way to reschedule
    without deleting and re-adding the interview.

* **New Feature**: Added the `search-interview` command
  ([#89](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/89))
  * What it does: Searches interviews by company name (`/c`), role (`/t`), and/or date (`/d`)
    using case-insensitive substring matching with AND logic.
  * Justification: As the interview list grows, users need a way to quickly find specific
    interviews without scrolling through the entire list.
  * Highlights: The filtering logic was refactored into an `Interview.matches()` method based
    on code review feedback, improving encapsulation and reusability.

* **New Feature**: Added the `clear-rejected` command
  ([#110](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/110))
  * What it does: Removes all internships marked as rejected in one step.
  * Justification: Users accumulate rejected entries over time that clutter the list.
    A batch-clear command is more efficient than deleting them one by one.

* **Enhancement**: Added `LocalDateTime` support for interview dates
  ([#102](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/102))
  * Previously interviews only stored a date (`LocalDate`). Enhanced to store both date and
    time (`LocalDateTime` with `yyyy-MM-dd HH:mm` format) so users can differentiate between
    interviews on the same day.

* **Enhancement**: Added `/help` flag support for `add-interview`, `update-date`, and
  `search-interview`
  ([#104](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/104))
  * Allows users to type e.g. `add-interview /help` to see usage information without
    leaving the application.

* **Code contributed**:
  [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=liny&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=functional-code&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=liny666&tabRepo=AY2526S2-CS2113-W10-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed issue tracking and linked PRs to issues for traceability.

* **Documentation**:
  * User Guide:
    * Added documentation for `add-interview`, `update-date`, `search-interview`, and
      `clear-rejected` commands
      ([#111](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/111)).
  * Developer Guide:
    * Added implementation details and design considerations for `add-interview`,
      `update-date`, `search-interview`, and `clear-rejected`
      ([#59](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/59),
      [#112](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/112)).
    * Added/updated UML diagrams: Interview class diagram, AddInterview sequence diagram,
      SetDeadline sequence diagram, SearchInterview sequence diagram.
    * Added user stories, NFRs, glossary, and manual testing instructions for my features.

* **Community**:
  * PRs reviewed (with non-trivial comments):
    [#55](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/55),
    [#81](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/81),
    [#107](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/107),
    [#108](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/108)
  * Addressed code review feedback across multiple iterations for search-interview
    ([#89](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/89)) — refactored filtering
    logic, removed redundant variables, and used direct field access based on reviewer
    suggestions.
