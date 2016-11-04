# advenjure-example

![Example game](example.gif)

Example game for the [advenjure](https://github.com/facundoolano/advenjure) text game engine.
You can play it online [here](https://facundoolano.github.io/advenjure/).

## Usage

Clone the respository and [Install leiningen](http://leiningen.org/#install) if necessary.
To run the example game on the terminal:

```
lein run
```

To run the example game on the browser, using figwheel for development:

```
lein figwheel dev
```

Then open `http://localhost:3449`.

To compile the optimized JavaScript to run in production:

```
lein cljsbuild once main
```

Then open `resources/public/index.html`.
