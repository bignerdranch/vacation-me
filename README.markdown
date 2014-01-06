# Vacation Me

## Running the app locally

You need Scala and Play.  I installed both with Homebrew.

You need a couple of environment variables defined:

```sh
APPLICATION_ID=
APPLICATION_SECRET=
OAUTH_REDIRECT_URI=http://localhost:9000/oauth_callback
```

Since I don't know how to load environment variables in Scala (tooling
is hard), I just created a shell script at `bin/play-development` like so:

```sh
#!/bin/sh

export APPLICATION_ID=
export APPLICATION_SECRET=
export OAUTH_REDIRECT_URI=http://localhost:9000/oauth_callback
play
```

I use this to fire up my play console.

You can get the right OAuth credentials (to use above) here:
[https://stable.bignerdranch.com/oauth/applications/11](https://stable.bignerdranch.com/oauth/applications/11).

## What it does

The objective of Vacation Me is simple: to let you know, at a glance,
who is on vacation at Big Nerd Ranch.  Looking up individual results
is tedious; let's let computers do sometihing for us.

## How it does it

Once you have authenticated, I'm using your OAuth credentials to look
at results in stable.  I look for all the vacation results for today,
where that involves looking for all the uncompensated, unbilled,
unacknowledged, unbooked, non-tentative results, then pulling on the
ones that are of the "vacation" type.  I'm thinking there should be an
easier way to do this, but I couldn't find one.  Then we merge those
results with their owners, and show them up.  Simple and easy.

## How it is written

I have no idea what I'm doing in this app.  It's my first attempt to
really use Scala (and the Play! framework).  It's complicated to me
and there is a lot going on I don't understand.  So the code is
somewhere between 'meh' and 'egads'.  I might cycle back and try to
clean things up--I know lots of what needs to happen but have zero
clue how to make it happen--though to be honest I'll probably get
distracted by a new app idea before I do that.

## How you can help

Add any features you'd like.  Pair with me to show me how to write
Scala that makes sense.  Change things that suck.  The usual drill.
The good thing is that I don't know how to write tests in Scala, so no
pull requests require tests. ;)
