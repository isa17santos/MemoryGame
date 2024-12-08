//-----------------------------------------   CONTEXT   -------------------------------------------------- 

        In this read me file, we will have very important notes about the project such as which users are
    created in the data base and their credentials, how to work with our local DataBase, etc.
        Also, in this file, you can read the notes of each first daily meeting of each sprint. In those daily 
    meetings we organized all the work, in the other ones we made a situation point to know what was already done or
    not. For this reason, we will only right the notes of the first daily meetings of each sprint.  

//-----------------------------------------   CONTEXT   -------------------------------------------------- 


//-----------------------------------------   IMPORTANT NOTES   -------------------------------------------------- 


    **************************************   DATABASE   *****************************************

        Our first idea was to use FireBase to do our remote DataBase, but when we saw that FireBase works with NoSQL and saw all the disadvantages that this would bring us. We decided that the best approach was to create a local DataBase in our project with SQLite. 
        Our DataBase is incorporated in our project repository and the only thing that you need to do is run the application in Android Studio. This will automatically run the DataBase and create the tables.
        It is important to note that every device has it's own DataBase with it's own data, so if you run the application in different types of devices, you will have different data in each device.


        **************************************   TABLES   *****************************************
          __________________                 ___________________                 ____________________
          |      Games      | -<0--------||- |       Users      | -||--------0>- |   Notifications  |
          |_________________|                |__________________|                |__________________|
          |     id (PK)     |                |     id (PK)      |                |      id(PK)      |
          |    attempts     |                |     username     |                |      message     |
          |     score       |                |     password     |                |    hasBeenRead   |
          |     time        |                |       coins      |                |       title      |
          |   board_size    |                | firstTimeLogging |                |    id_user (FK)  |
          |     date        |                |__________________|                |__________________|
          |   id_user (FK)  |
          |_________________|


    ************************************** VALID USERNAMES & PASSWORDS  *****************************************

        Username:           Password:
           isa                isa123
          bruno              bruno123 
         carolina           carolina123 
          duarte             duarte123 
           test               test123 
         testcoins          testcoins123


    **************************************   NOTIFICATIONS PAGE   *****************************************

        When you are in the Notifications Page and have many notifications, in order to see all of them, you need to click on the mouse and drag it. The scroll will not work here.


//-----------------------------------------   IMPORTANT NOTES   -------------------------------------------------- 



//--------------------------------------  DAILY MEETINGS   ----------------------------------------------- 

    Sprints:  In total, our timeline has 6 sprints, from october 29th to december 10th


    **************************************  SPRINT 1 (week 1)  *****************************************
    Sprint start: 2024/10/29
    Sprint end: 2024/11/05

    First daily meeting:
        Scrum Master: Bruno

        User Stories and tasks of Sprint 1:
            - US1 - Design the Login Page                         (Duarte)
            - US2 - Design the Dashboard (Anonymous)              (Carolina)
            - US3 - Design the Dashboard (User)                   (Isa)
            - T1 - Learn more about FireBase + Android Studio     (Bruno)

        Story Points (Metodo Delphi):
                      Isa      Bruno      Carolina     Duarte       Decisão_Final
            US1 -      3         3            3          3                3
            US2 -      2         2            2          2                2
            US3 -      3         3            3          3                3
            T1 -       3         3            3          3                3

        Horas que cada elemento vai colocar por semana (sprint):
            Isa      Bruno      Carolina     Duarte
             6         6           6           6


    **************************************  SPRINT 2 (week 2)  *****************************************
    Sprint start: 2024/11/05
    Sprint end: 2024/11/12

    First daily meeting:
        Scrum Master: Isa

        User Stories, tasks and subtasks of Sprint 2:
            - US4 - Create the MemoryGame (3*4) (Anonymous user)  
                - ST1 - Design the Game User Interface                                                   (Carolina)
                - ST2 - Create the default icons for the cards and Randomize the appearance of the cards (Duarte)
                - ST3 - Create the click events (recognition of cards when clicked)                      (Bruno)
            - US5 - Implement the Dashboard (User)                                                       (Isa)
            - US6 - Implement the Dashboard (Anonymous)                                                  (Carolina)
            - US7 - Implement the Login Page                                                             (Duarte)
            - US8 - Implement a page to select the board size (User)                                     (Isa)
            - T2 - Research the best approach to the creation of the Memory Game                         (Bruno)
            
        Story Points (Metodo Delphi):
                            Isa      Bruno      Carolina     Duarte       Decisão_Final   
            US4 -           11         10          10           10              10
               ST1 -        3          3           3            3               3
               ST2 -        4          3           3            3               3
               ST3 -        4          4           4            4               4
            US5 -           4          5           4            4               4
            US6 -           3          4           4            4               4
            US7 -           3          4           4            4               4
            US8 -           2          2           2            3               2
            T2 -            2          2           2            2               2


        Horas que cada elemento vai colocar por semana (sprint):
            Isa      Bruno      Carolina     Duarte
            12        12          12          12            


    **************************************  SPRINT 3 (week 3)  *****************************************
    Sprint start: 2024/11/12
    Sprint end: 2024/11/19

    First daily meeting:
        Scrum Master: Duarte

        User Stories, tasks and subtasks of Sprint 3:
            - US4 - Create the MemoryGame (3*4) (Anonymous user) (Continuation)
                ST4 - Implement the game logic (check if the card 1 and 2 are matching)        (Bruno)
                ST5 - Update the information during the play                                   (Duarte)
                ST6 - Make timer run during play                                               (Carolina)
            - T3 - Error fixing                                                                (Bruno)
            - T4 - Randomnize fix                                                              (Duarte)
            - T5 - Make all the layouts responsive                                             (Isa)
            - T6 - Upgrading Katalon tests                                                     (Isa)
            

        Story Points (Metodo Delphi):
                            Isa      Bruno      Carolina     Duarte       Decisão_Final
            US4 -            10        10           11          10              10
                ST4 -        5         5            5           5               5
                ST5 -        2         2            3           2               2
                ST6 -        3         3            3           3               3
            T3 -             1         1            1           1               1 
            T4 -             2         2            2           2               2 
            T5 -             5         4            4           4               4
            T6 -             3         3            3           4               3 


        Horas que cada elemento vai colocar por semana (sprint):
            Isa      Bruno      Carolina     Duarte
            13        11           6           7

    **************************************  SPRINT 4 (week 4)  *****************************************
    Sprint start: 2024/11/19
    Sprint end: 2024/11/26

    First daily meeting:
        Scrum Master: Carolina

        User Stories, tasks and subtasks of Sprint 4:
            - US4 - Create the MemoryGame (3*4) (Anonymous user) (Continuation)
                - ST9 - Create a pop up window for the end of the game                                      (Bruno)
            - US9 - Add a Cost to Each Game (except 3*4)                                                    (Bruno)
            - US10 - Add a 3*4 Gamming Board (Registered)                                                 (Carolina)
            - US11 - Add a 4*4 Gaming Board (Registered)                                                    (Duarte)
            - US12 - Add a 6*6 Gaming Board (Registered)                                                    (Isa)
            - US13 - Implement Hints's System
                - ST7 - Implement Buy Hints Feature                                                         (Isa)
                - ST8 - Implement hint's logic                                                              (Duarte)
            - T7 - adding back actions                                                                      (Isa)
            - T8 - Creating missing layouts and adding Scoreboard button to the dashboard anonymous layout  (Isa)

        Story Points (Metodo Delphi):
                      Isa      Bruno      Carolina     Duarte       Decisão_Final
            US4 -      4         4            4           4               4
               ST9 -   4         4            4           4               4 
            US9 -      3         3            3           3               3
            US10 -     5         5            5           5               5
            US11 -     5         5            5           5               5
            US12 -     5         5            5           5               5
            US13 -     6         6            6           5               6
                ST7 -  2         2            2           1               2
                ST8 -  4         4            4           4               4
            T7 -       1         1            2           1               1
            T8 -       2         3            2           2               2


        Horas que cada elemento vai colocar por semana (sprint):
            Isa      Bruno      Carolina     Duarte
            18        14          10         15

    **************************************  SPRINT 5 (week 5)  *****************************************
    Sprint start: 2024/11/26
    Sprint end: 2024/12/03

    First daily meeting:
        Scrum Master: Isa

        User Stories and tasks of Sprint 5:
            - US14 - Implement Notification System                     (Duarte)
            - US15 - Implement a Reward System                         (Bruno)
            - US16 - Implement Game History Feature (Registered)       (Carolina)
            - US17 - Implement Scoreboard Feature                      (Bruno)
            - US18 - Implement login Validation                        (Isa)
            - T9 - Personal scoreboard attempts crash                  (Bruno)
            - T10 - Remaking all katalon tests                         (Carolina)
            - T11 - create database                                    (Isa)
            - T12 - Create test layout                                 (Duarte)
            - T13 - Improving pop ups                                  (Carolina)

        Story Points (Metodo Delphi):
                      Isa      Bruno      Carolina     Duarte       Decisão_Final
            US14 -     6         6           6           6                6
            US15 -     5         6           6           6                6
            US16 -     6         6           6           6                6
            US17 -     7         6           6           6                6
            US18 -     5         5           6           5                5
            T9 -       1         1           1           1                1
            T10 -      7         7           7           7                7
            T11 -      4         4           4           4                4
            T12 -      4         3           4           4                4
            T13 -      6         5           5           5                5

        
        Horas que cada elemento vai colocar por semana (sprint):
            Isa      Bruno      Carolina     Duarte
             24       24          20           18

    **************************************  SPRINT 6 (week 6)  *****************************************
    Sprint start: 2024/12/03
    Sprint end: 2024/12/10

    First daily meeting: 
        Scrum Master: Bruno

        User Stories and tasks of Sprint 6:
            - US19 - Purchasing coins                            (Duarte)
            - T14 - Fixing errors from history                   (Carolina)
            - T15 - Fixing errors from notifications             (Duarte)
            - T16 - Fixing errors from coin system               (Bruno)
            - T17 - Fixing errors from reward system             (Bruno)
            - T18 - Fixing errors from pop ups                   (Isa)
            - T19 - Fixing errors from scoreboard                (Bruno)
            - T20 - Create a read me file                        (Pair programming)
            - T21 - Remaking some of the katalon tests           (Pair programming)

        Story Points (Metodo Delphi):
                      Isa      Bruno      Carolina     Duarte       Decisão_Final
            US19 -     3         3           3            2               3
            T14 -      1         2           2            2               2
            T15 -      1         1           1            1               1
            T16 -      1         1           2            1               1
            T17 -      1         1           1            1               1
            T18 -      4         4           4            4               4
            T19 -      3         2           2            2               2
            T20 -      4         4           4            4               4
            T21 -      8         8           8            8               8


        Horas que cada elemento vai colocar por semana (sprint):
            Isa      Bruno      Carolina     Duarte
            20        20           15         15

//--------------------------------------  DAILY MEETINGS   ----------------------------------------------- 


//------------------------------------  FINAL COUNT OF STORY POINTS   --------------------------------------------- 

    Isa      Bruno      Carolina     Duarte
    42        42          40           40


//------------------------------------  FINAL COUNT OF STORY POINTS   --------------------------------------------- 
