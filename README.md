# Introduction
This Project is constructed by _Swimiltylers_ (id: 151220145) per
se. It is aimed at simulating the plots of cartoon _Huluwa_. The
characters are: Huluwas, grandpa, minions, serpent and 
scorpion. We will take a close look at its whole formation in this manual.

The whole dependency structure of the project is illustrated [here](./UML/Project.png). 
If you want display the demo, please run our __NEW__ descriptor `Narrator`, or ./platform/Plate.java in an old style.

We have apply some __new__ __features__ to our project, which is highlighted 
by __'*'__ after the title. If you want to view the history version, 
please check Git history in my personal [Github repository](https://github.com/SwimilTylers/maven-java-2017-huluwa).

## Background Emoji*

At this revision, we apply emoji-based background instead of naive word implications.
Meanwhile, it is noticeable that the characters have been equipped with emoji as well.
In order to perform this new feature, we have derive an inherit class - `PlateMapModule_Background`, relative to 
`PlateMapModule`, and `PositionWithBackground` to `Position`. _Trinity_ isolates PlateMapModule from Plate, which
is facilitated from it when deploy our new MapModule mentioned above. The description of emoji lies in
`enum BACKGROUDNS` and `enum FOREGROUNDS`. 

## Factory and decorator*
We add factory in this revision. All the factory class locates in utils.factory. The implement of factory heads for the 
problem which allows PlateMapModule to generate Map flexibly. Meanwhile, factory class `PlateMapModules` and its 
derivative `PlateMapBKModules` integrate deployment and design of the Map formation.

    static public PlateMapModule_Background InitializeMapModule(PlateSettings Settings, Enum EnumOptions, ArrayList<Pair<BACKGROUNDS, Layout>> layouts);

Designing pattern decorator has been employed on `Plate`, as it is shown below

    static public Plate CreateRealm(PlateSettings Settings, PlateMapModule MapModule, PlateLayoutManipModule LayoutModule, Beings... Characters);
    
The diversity of combination has warrant the fluidity of project structure.

## Exceptions and Generic*

    public interface PFactory<P extends Position> {
        P NewPosition(Object... GeneralOptions) throws Exception;
        P NewPosition(Enum EnumOptions, Object... GeneralOptions) throws Exception;
    }

## Maven it*
The structure of this project has transformed into total new form.
As you can see, that was maven. With the help of it, we can build
our project automatically.

## Trinity

This time, we add a new feature in our project. In order to 
follow the SOLID principle, we apply several modifications to our
 our object formation. The main work is to separate the 'huge'
 `Plate` object. In our new revision, the `Plate` is formed of 
three different objects: `PlateLayoutManipModule`, `PlateMapModule`, 
and modified `Plate`.  `PlateMapModule` is responsible for 
Coordination-relates, while `PlateLayoutManipModule` for the transformation 
of Layout. Our new `Plate` is now free from redundant functionality and 
focuses on Character deployment. 

![Trinity](./UML/Package%20plate.png)

## Scepter

Resemble to the above effort, we apply interface to standardize 
the behavior of Superiors. Thus we employ `Representatives`. 
With this interface, we want to decouple the application from 
the concrete implementation of the lower layer.

## World of hierarchy

>War is Peace. Freedom is Slavery. Ignorance is Strength.

In the realm created in this project, there are three 
main components: _character_, _platform_ and _utils_.
It is clear that we can get Inheritance Structure in _character_
as followed

    Beings  -> Subordinate  -> Minion
        |   -> Grandpa      |-> Scorpion
        L   -> Serpent      L-> Huluwa  -> Dawa
                                        |-> Erwa
                                        ...
                                        L-> Qiwa
 
Particular, in this revision, Grandpa creates, positions,
and range Huluwas, which means it is prohibited to access
or control Huluwas directly from the outside. Serpent to
Scorpion and Minions resembles, though categorized as
_villains_ rather than _heroes_.

    public void Serpent::Recruit(LayoutBrief initLayout);
    public void Serpent::DesignateTroops(LayoutBrief layout);

For individuals, the action is activated by messages from
outsider, for _grandpa_ or _serpent_, or their superior.
The typical message chain is as followed:
    
    World -> superior -> subordinates -[feedback to Position]-> World

Their feedback functions directed on Position they stand on, all
some similar way. We modify the method as close as natural
interaction, which sometimes means it is distributed rather than
global method.
    
    public boolean Being::FindMyPlaceInLayout(Layout layout)
    
Meanwhile, some character contains specific intra-class information.
As for Huluwas, order of seniority is declared in _Huluwa_. Similar
style of elaboration spread out the whole project, i.e. class _Plate_.

    static final public ComparingInterface SenioritySorting_f2s = new SenioritySorting() {...};
    abstract class SenioritySorting extends ComparingInterface {...}
    
## Ship In A Bottle

>Ashes to Ashes, and Dust to Dust.

In the _platform_ section, we create a background where 
all characters communicate and interact. It is the base of
actions. Therefore, when create a realm, we need to explicitly
point out initCharacters and their coordinates, since 
coordination is one of the principle attribute to those
initCharacters.

    Plate world = Plate.CreateRealm(PlateSettings.Regularized, grandpa, serpent);
    
NOTICE: for all _Beings_, you need to point out the birthplace
when constructing one. In some rare scenarios, one _Being_
can generated with _null_ birthplace, but it is an
intermediate process within the procedure of particular
ops.

Constrained by the platform, any character, or even some
utils, cannot get rid of the world they live in. _BasePosition_
characters resides in, _Layout_ Sorter based on, _Coordinate_
position possesses - all of them relate closely to the platform
they live in.

    public Layout::Layout(PlatformMapModule platform, Coordinate... obj);
    

## Between Cartesian Products

>Infinite Diversity in the infinite Combination.

What means when we formulate our methods as a 'universe' one.
In order to realize it, we plot a wide range of base-class.
We can further this base-class to the specific class we need.
At the same time, we use more some general media the modify
the action of out method. Take Sorter as an example.

    void Sorter::Sort(Layout array, ComparingInterface cmpInterface);

_Layout_ is a sequence of BasePositions, which contains direction
information. _ComparngInterface_ is a general comparing method, which
determines the correlation of two variants. It is notable that
_Sorter_ per se is an interface, and we can apply it to some
specific methods.
    
    public class BubbleSort implements Sorter;
    
We can see similar methods throughout the whole project. Thus,
assisted by the power of combination, we can display our
simulation in diverse approaches.
