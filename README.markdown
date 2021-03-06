# doll-delivery

This program completes the
[challenge](https://github.com/postnati/doll-delivery) set forth by
Blue Medora.

I have used Dijkstra's algorithm for finding the "shortest path"
between two vertices on a connected, undirected, weighted graph. The
algorithm is well-known; an overview can be found on [Wikipedia](http://en.wikipedia.org/wiki/Dijkstra's_algorithm).


## Usage

`doll-delivery` requires [sbt](http://www.scala-sbt.org/). It was
created and tested with sbt 2.1.1.

### Running

To determine the shortest path between two vertices, run `sbt "run
path/to/data/file.txt"` in the project's root directory. Note the
quotes; `sbt` chokes without them.

Alternatively, start `sbt` in the project's root directory and type
`run path/to/data/file.txt` in the prompt.

### Testing

To run the tests, run `sbt test` in the project's root directory.

Alternatively, start `sbt` in the project's root directory and type
`test` in the prompt.

To test `doll-delivery` programmatically, simply import the object
`com.garslo.dolldelivery.DeliverySolver` and run
`DeliverySolver.dijkstra(filename, syntaxType)`, where `filename` is
the path to your test file, and `syntaxType` is either `MapSyntax` or
`CsvSyntax`, dependent upon your input file's formatting.

### Input Format

`doll-delivery` accepts two types of formatting: a csv-like format and
a Scala Map-like format. See
`src/test/scala/com/garslo/dolldelivery/resources` for examples.

#### CSV Format

Any line equivalent to `startingLocation: <string>` (up to whitespace)
will be treated as the starting vertex declaration. Similarly, any
line equivalent to `targetLocation: <string>` will be treated as the
ending vertex declaration.

The edges are specified in CSV format. For example, `A, B, 4` would
indicate an edge between vertex `A` and vertex `B`, with a weight of
`4`.

Any line not fitting these specifications will be *silently* ignored.

#### Map Format

The Map-like format takes its lead from the example data found in the
challenge.

Similar to the above, any line equivalent to `startingLocation:
<string>` (up to whitespace) will be treated as the starting vertex
declaration. Similarly, any line equivalent to `targetLocation:
<string>` will be treated as the ending vertex declaration.

The edges are specified in a Scala Map format. For example,
`Map("startLocation" -> "first vertex", "endLocation" -> "second
vertex", "distance" -> 10)` would indicate an edge between vertex
`first vertex` and vertex `second vertex`, with a weight of `10`.

Any line not fitting these specifications will be *silently* ignored.

## Options

No options, however a data file must be specified.

## Examples

```sh
$ sbt "run src/test/scala/com/garslo/dolldelivery/resources/test_data_1.txt"
...
Map(distance -> 31, path -> Kruthika's abode => Brian's apartment => Wesley's condo => Bryce's den => Craig's haunt)
..
$
```

## Limitations

`doll-delivery` makes no accommodation for unconnected graphs. That
is, there must be some path from each vertex to any other vertex in
the graph.
