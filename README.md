# House Taken Over

Text adventure inspired by the short story "House Taken Over", written by Julio Cortázar.
The game uses the [advenjure](https://github.com/facundoolano/advenjure) engine.

## Usage

Clone the respository and [Install leiningen](http://leiningen.org/#install) if necessary.
To run the game on the terminal:

```
lein run
```

To run the game on the browser, using figwheel for development:

```
lein figwheel dev
```

Then open `http://localhost:3449`.

To compile the optimized JavaScript to run in production:

```
lein cljsbuild once main
```

Then open `resources/public/index.html`.
