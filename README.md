## Introduction to my idea

## Hill-climbing

#### OptimizeSolution

> Main.class to run the program

#### SolutionHelper

> Helper class to write all main methods

#### Solution

> My Idea
> > Generate selected samples based on Random method
>
> > Generate all possible results
>
> > Generate all cases need to cover
>
> > Store in a map
>
> > When j s is different each key in the map might have multiple entries
>
> > Calculate the maximum number of key each possible result can remove
>
> > Selected the possible result that can remove the most cases need to cover
>
> > Remove key and in a while loop when the map has zero entry the solution is found

### Problem

> > The solution is not optimal
>
> > Since it might get into a local maxima

### Improvement

> Algorithm
> > Avoid local maxima
>
> Data Structure
> > How to accelerate get result process
>
> > Accelerate remove and search process
>
> > Replace Map with other kind of structure