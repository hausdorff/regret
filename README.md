# regret

**Regret** is an open source, MVC-like framework for machine learning (ML) systems. Our goal is to greatly simplify the development process of complex, distributed ML systems, in the same way frameworks like [Django](https://www.djangoproject.com/) and [Rails](http://rubyonrails.org/) simplified web development.

Regret aims to be completely full-stack, responsible for the entire ML system from data ingest, to cleaning, to storage, to prediction, and so on. Regret emphasizes use of design principles like [active record](http://en.wikipedia.org/wiki/Active_record_pattern), [convention over configuration](http://en.wikipedia.org/wiki/Convention_over_configuration), and [don't repeat yourself](http://en.wikipedia.org/wiki/Convention_over_configuration) so that as much of the cruft as possible is hidden, freeing the programmer to concentrate on iterating the model.

## Project goals

Our core goal is to handle as many of the "mundane" things (e.g., data persistence) as possible in the course of declaring all the "normal" things about your model (e.g., your features and their types).

Other goals include:

* **Special attention for important-but-unsexy practicalities.** Most ML libraries leave it up to the user to write code for things like data cleaning and cross-validation. Since every nontrivial system must write code for such things, we believe logic for handling them should be baked right into the logic of declaring your ML system.

* **Natively supports distributed ML systems.** Most ML libraries concentrate on scaling the training algorithms, but the entire system needs to scale. For example, if the servers responsible for feature extraction go down, or there is network latency, then your ML system will output garbage. By emphasizing modularity, and building on scalable systems like [Storm](https://github.com/nathanmarz/storm), regret seeks to remove as many scaling problems as possible from the picture.

* **Compatible with robust, expressive modeling tools.** Rather than reinventing the wheel, regret seeks to be compatible with familiar tools like [Mahout](http://mahout.apache.org/). The large number of ML libraries available for JVM languages is one of the reasons regret is built on the JVM.

* **Separation between "dev" mode and "production" mode.** In production, the goal is mainly to make the system respond quickly. In development, it is generally more important for the system to make debugging and profiling easy (e.g., by turning off caching and producing much more thorough logs). Since these modes are not the same, it is important that regret provide a formal distinction between them.

* **Easy to transition between prototype and production.** We believe that a developer should be able to prototype the system on a laptop, and then `git push` to send the repot to a distant cluster that simply flips a switch and scales the system accordingly.

* **Database/data persistence completely abstracted away.** Much like Rails, the developer should only have to specify a model, *i.e.*, what the data is. The system should simply turn this into an ORM and handle all the database details behind the scenes.

* **Uses robust database technology.** The worst place to take a risk is on your primary data store. Academic technology tends to use unproven database technology because building ML-optimized databases is cool. We agree it's totally sweet, but the goal of the system is to be scalable. That means choosing technology that will with 100% certainty be supported in 10 years, in addition to not having a bug causing catastrophic data loss. You don't recover from these errors.

* **Highly modular.** In the same way that Rails and Django (and other MVC frameworks) split web app logic between models, views, and controllers, I think we can split ML tasks between "types" of module. So in Rails you can type `rails generate controller CreditCard` and you have a template for dealing with all the control logic of something called `CreditCard`. It is easy to reason about this code, and it is easy to think about how your data persists through the system.

* **Internet capability baked right in.** Most of the time your data is coming to your ML system via network. In a lot of cases it gets communicated to users or other parts of your infrastructure via network. So, I want to be able to access the system both via library call and web API. If I send a GET, I want something like JSON coming back with predictions. If I send a POST, I want it to learn from the data I gave it.

* **Strict separation of experiments/ability to prototype many experiments in parallel.** Strict separation of experiments allows them to be deployed in parallel, makes them cleaner to reason about, and makes experiments reproducible by simply copying the relevant properties files.

* **Accessible via REPL.**

* **Handles multiresolutional data, tabular data, relational data, etc.** This is all down the line but also important.



## License

Copyright © 2013

Distributed under the MIT license.
