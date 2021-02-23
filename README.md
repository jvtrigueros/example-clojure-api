# example-clojure-api
> This is an example of a Clojure application that follows a spec to provide a CLI and HTTP interface.

# Assumptions

We will make the following assumptions based on the spec provided.

## Input Size

The input is specified both as being **one** file and **three** files:

> ...app that takes as input a file with a set of records...

> The input is 3 files...

To make things simpler, the CLI application will accept any number of files, the entrypoint will assume it's a collection
of files.

## Input Format

Each file will be of only **one** type, that is the following isn't a valid format:

```
LastName | FirstName | Email | FavoriteColor | DateOfBirth
LastName, FirstName, Email, FavoriteColor, DateOfBirth
LastName FirstName Email FavoriteColor DateOfBirth
LastName | FirstName, Email FavoriteColor | DateOfBirth
```

But these are:

```
LastName | FirstName | Email | FavoriteColor | DateOfBirth
LastName | FirstName | Email | FavoriteColor | DateOfBirth
LastName | FirstName | Email | FavoriteColor | DateOfBirth
```

```
LastName, FirstName, Email, FavoriteColor, DateOfBirth
LastName, FirstName, Email, FavoriteColor, DateOfBirth
LastName, FirstName, Email, FavoriteColor, DateOfBirth
```

```
LastName FirstName Email FavoriteColor DateOfBirth
LastName FirstName Email FavoriteColor DateOfBirth
LastName FirstName Email FavoriteColor DateOfBirth
```

The application will determine the delimiter and use it to parse the given file, but if invalid input is provided, the
application will behave in a "garbage in garbage out" manner.

## Input Values

All fields will be treated as Strings and there will be no validation, e.g. _emails will be taken as is_. The exception is
`DateOfBirth` this needs to be parsed to make it sortable, the input format is the same as the output `MM/DD/YYYY`
