# JBoto
This is a very simple Java pipeline/assembly line library. It lets you divide a complicated process into several smaller, perhaps reusable steps. Simple branching and looping is provided.

It's configured using a JSON file. For instance, to build a car:

`[
	{ "type": "container", "id": "main", "classname": "--none--", "commands": [
		{ "type": "command", "classname": "BuildFrame" },
		{ "type": "foreach", "classname": "GetHeadlightList", "commands": [
			{ "type": "command", "classname": "AddHeadlight" },
			{ "type": "if", "classname": "HasEnoughHeadlights", "commands": [
				{ "type": "break", "classname": "--none--"}
			]}
		]},
		{ "type": "command", "classname": "PaintIt" },
		{ "type": "if", "classname": "HasLuxuryTrim", "commands": [
			{ "type": "command", "classname": "AddWoodPaneling" }
		]},
		{ "type": "command", "classname": "RegisterIt" }
	]}
]`

You provide a class that implements `IProduct`, in this case `Car`. That's passed to each step, which adds things to the car. `BuildFrame` adds a frame to the car,
`GetHeadlightList` returns a list of headlights, `AddHeadlight` adds each of those headlights to the car, etc. However, `GetHeadlightList` returns rear headlights too
and since those aren't a great feature, `HasEnoughHeadlights` causes a break out of the loop after two headlights have been added.

Each of the `classname` values are the names of classes that are instantiated to perform a certain task.

There are currently four options for `type`: `command` (for basic commands), `foreach` (to loop over a `List<Object>`), `if` (runs its `commands` if the class returns `true`),
and `break` (breaks out of a `foreach` loop).

See the [example](https://www.markdownguide.org/cheat-sheet/) for more.

If there's any interest, more options, the ability to take classes from different packages, and package aliases can be added. A Java-like language could also be used in
place of the JSON.
