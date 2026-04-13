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
    `Internship` objects stays consistent. Added date-in-past validation
    (comparing the parsed `LocalDateTime` against `LocalDateTime.now()`) and duplicate
    interview detection to prevent user errors. Later hardened with strict calendar
    validation using `ResolverStyle.STRICT` to reject impossible dates like `2026-02-30`
    ([#210](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/210)).

* **New Feature**: Added the `update-date` command
  ([#31](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/31),
  [#86](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/86))
  * What it does: Allows the user to update the date and time of an existing interview.
  * Justification: Interview schedules change frequently. Users need a way to reschedule
    without deleting and re-adding the interview.
  * Highlights: Fixed a silent off-by-one indexing bug (PE-D #192) where successive
    `update-date` calls would target the wrong interview after dates were reordered.
    The fix indexes directly into the sorted `InterviewList` and re-sorts the backing
    list in-place after mutation, so `list-interview` and `update-date` always refer to
    the same ordering. Also added past-date rejection for consistency with `add-interview`
    ([#210](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/210)), and improved the
    error message when no interviews are scheduled to guide users toward `add-interview`
    ([#213](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/213)).

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
  * What it does: Removes all internships marked as rejected in one step, along with their
    associated interviews to prevent orphaned records.
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

* **Enhancement**: Added defensiveness to interview and rejected commands
  ([#123](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/123))
  * Added logging, assertions, and JavaDoc across `AddInterviewCommand`,
    `SetInterviewDeadlineCommand`, `SearchInterviewCommand`, and `ClearRejectedCommand`.
  * Updated `ClearRejectedCommand` to delete associated interviews, preventing orphaned records.

* **Enhancement**: Fixed logger encoding on non-English locales
  ([#139](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/139))
  * Console log output showed garbled question marks on systems with non-UTF-8 default
    encodings (e.g. Windows with Chinese locale). Fixed by explicitly setting the console
    handler encoding to UTF-8.

* **Enhancement**: Refactored date mutation into `InterviewList.setDateFor()`
  ([#210](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/210))
  * Extracted the `interview.setDate()` + list re-sort logic from `SetInterviewDeadlineCommand`
    into a dedicated `InterviewList.setDateFor(int index, LocalDateTime date)` method,
    keeping mutation and ordering concerns inside `InterviewList`.

* **Code contributed**:
  [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=liny&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=functional-code&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=liny666&tabRepo=AY2526S2-CS2113-W10-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed releases [v1.0](https://github.com/AY2526S2-CS2113-W10-4/tp/releases/tag/v1.0)
    and [v2.0](https://github.com/AY2526S2-CS2113-W10-4/tp/releases/tag/v2.0) on GitHub
    (2 releases).
  * Managed issue tracking and linked PRs to issues for traceability.

* **Documentation**:
  * User Guide:
    * Added documentation for `add-interview`, `update-date`, `search-interview`, and
      `clear-rejected` commands, including 24-hour time format clarification, invalid
      calendar date rejection, and re-sort behaviour after `update-date`
      ([#111](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/111),
      [#130](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/130)).
  * Developer Guide:
    * Added implementation details, validation tables, and design considerations for
      `add-interview`, `update-date`, `search-interview`, and `clear-rejected`
      ([#59](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/59),
      [#112](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/112),
      [#130](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/130)).
    * Added/updated UML diagrams: Interview class diagram, AddInterview sequence diagram,
      SetDeadline sequence diagram, SearchInterview sequence diagram, ClearRejected sequence
      diagram
      ([#137](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/137),
      [#141](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/141),
      [#153](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/153)).
    * Added manual testing instructions, user stories, NFRs, and glossary for my features.
    * Updated DG to document new validation behaviour (strict calendar parsing, past-date
      rejection for `update-date`, improved index error messages).

* **Community**:
  * PRs reviewed (with non-trivial comments):
    [#55](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/55),
    [#81](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/81),
    [#87](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/87),
    [#98](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/98),
    [#107](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/107),
    [#108](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/108),
    [#124](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/124),
    [#125](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/125),
    [#136](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/136),
    [#138](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/138),
    [#142](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/142),
    [#143](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/143),
    [#206](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/206),
    [#208](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/208),
    [#211](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/211)
  * Addressed code review feedback across multiple iterations for search-interview
    ([#89](https://github.com/AY2526S2-CS2113-W10-4/tp/pull/89)) — refactored filtering
    logic, removed redundant variables, and used direct field access based on reviewer
    suggestions.
