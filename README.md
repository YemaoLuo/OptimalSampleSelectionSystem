## Group work for MUST AI Course(Junior year)
## [Report for this project](https://github.com/YemaoLuo/OptimalSampleSelectionSystem/blob/main/Artificial_Intelligence_Report.pdf)
## [User manual for the releases](https://github.com/YemaoLuo/OptimalSampleSelectionSystem/blob/main/User_Manual.pdf)
## Introduction to my idea
## Not opensourced until September 2023
## Advanced greedy search
## [Android app OSSS](https://github.com/YemaoLuo/AIApplication)

#### algorithm.OptimizeSolution

> Main.class to run the program

#### algorithm.SolutionHelper

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
>
> > Time mainly spent on getting candidate result

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
>
> > Add multi thread to accelerate the process
> > > Reduced half of time cost when n is large
> >
> > > v1_1 add multi thread to read possibleResults
>
> > v2.0 get rid or the map and use the number of cases satisfy to judge
> > > Reduced tremendous time cost!!!
> >
> > v2.1 skip some possibleResults
> > > Reduced some time cost
> > >
> > v2.2 skip some nCj
> > > Reduced some time cost
