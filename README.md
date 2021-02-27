# example-clojure-api

> This is an example of a Clojure application that follows a spec to provide a CLI and HTTP interface.

## Usage

The CLI application can be use from `leiningen` or `babashka`

```
lein run -m example-clojure-api.cli resources/test-data.csv resources/test-data.psv resources/test-data.tsv
```

```
bb --classpath src -m example-clojure-api.cli resources/test-data.csv resources/test-data.psv resources/test-data.tsv
```

This will generate three files sorted as specified.

The HTTP application can be run with `leiningen` as follows:

```
PORT=3000 lein run -m example-clojure-api.http.main
```

## Tests

All tests can be run with:

```
lein test
```

# CLI App Assumptions

We will make the following assumptions based on the spec provided.

## Input Size

The input is specified both as being **one** file and **three** files:

> ...app that takes as input a file with a set of records...

> The input is 3 files...

To make things simpler, the CLI application will accept any number of files, the entrypoint will assume it's a
collection of files.

## Input Format

Files can **only** be delimited by pipe(|), comma(,), or space( ). Mixing and matching is allowed the following are
valid:

```
LastName | FirstName | Email | FavoriteColor | DateOfBirth
LastName, FirstName, Email, FavoriteColor, DateOfBirth
LastName FirstName Email FavoriteColor DateOfBirth
LastName | FirstName, Email FavoriteColor | DateOfBirth
```

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

```
LastName, FirstName | Email              FavoriteColor DateOfBirth
LastName            , FirstName      |        Email , FavoriteColor,DateOfBirth
LastName FirstName Email FavoriteColor DateOfBirth
```

The application expects there to be a value for each column, if one is missing the row will be discarded. Because we
allow mixing and matching, no values can contain a pipe(|), comma(,), or space( ).

These are example of an invalid rows:

```
LastName * FirstName * Email * FavoriteColor * DateOfBirth
LastName | First Name | Email | FavoriteColor | DateOfBirth
LastName, Email, FavoriteColor, DateOfBirth
```

## Input Values

All fields will be treated as Strings and there will be no validation, e.g. _emails will be taken as is_. The exception
is
`DateOfBirth` this needs to be parsed to make it sortable, the input format is the same as the output `M/D/YYYY`

## Output

Will be generated as CSV.

## HTTP App Assumptions

Available routes

```
POST /records
GET /records/email
GET /records/birthdate
GET /records/name
```

### POST /records

This endpoint _one_ input line with the format specified in the CLI application. Body should be formatted as text,
here's an example curl request:

```
curl --request POST \
  --url http://localhost:3000/records \
  --header 'Content-Type: text/plain' \
  --data 'Zenia,Molineux,zmolineux39@vkontakte.ru,Red,4/19/2020'
```

For all successful responses, an `OK` in plaintext will be return. For any malformed input, a "Bad Request" will be
issued.

### GET /records/*

All of these endpoints will return the data sorted using the functions in the CLI application, here's a sample curl
request:

```
curl --request GET \
  --url http://localhost:3000/records/email
```

The result will be `application/json` and will contain two keys `count` and `results`. Where `count` is the number of
results and `results` will be an array of the records. Each record will also be a JSON object with the following
keys `last-name`, `first-name`, `favorite-color`, `email`, and `dob`. Here's the sample output:

```json
{
  "size": 1,
  "results": [
    {
      "last-name": "Andria",
      "first-name": "Scannell",
      "email": "ascannelllp@behance.net",
      "favorite-color": "Red",
      "dob": "4/23/2002"
    }
  ]
}
```
