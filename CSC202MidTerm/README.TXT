PROJECT TITLE: CSC202MidTerm
AUTHORS: Matt Warman
VERSION: 1.5
DATE: 03/16/2015


CHANGELOG

--PayrollCalculator--

Version 1.5
    Minor UX improvement - Added the ability to type the enter key while the "Add" button is selected instead of
        having to press the button. This is useful for people who use the TAB button and accidently tab one too many
    Changed the UI to a light greay color - easier on the eyer
    Changed the text area output background to match the window background. This reinforces the fact that it is
        un-editable and looks more professional
    Added a black border to the text area output

Version 1.4
    Fixed an order or operations error which resulted in inaccurate total payment calculations
    Made a slight whitespace adjustment

Version 1.3
    Implemented CSS
    Changed to monospace font to alleviate spacing headaches
    Implemented function which turns incorrect text red then back to black once it has been fixed
    Implemented max int checker for employee number
    Implemented dynamically addad whitespace for employee number and base pay seperation

Version 1.2
    Added horizontal bar between headers and employee listings
    Shortened top bar text fields
    Added ability for the user to press the enter key in some situations instead of having to press buttons
    Made the output area un-editable
    Made the output area semi-greyed out
    Slightly adjusted Total Amount location, lineing it up with employee pay amounts
    Vertically centered Total Amount between output box and the bottom of the main window
    Set the focus to return to the Employee Number field after new employee addition
    Slightly altered reset dialog box size

Version 1.1
    Fixed a bug in which overtime was not factored in to total pay calculation
    Added a reset button which gives the user the option to erase all employee objects from the table

Version 1.0
    Added in overtime calculation
    Removed the deprecated "Refresh" button
    Made raise selection cause instant refresh
    Added initial headers
    Fixed header spacing

Version 0.4
    Fixed bug where hours would be accepted even if they were over the maximum allowed
    Made the Total Amount field uneditable
    Fixed a bug that caused and "Unknown Error" (NullPointerException) when then user clicked "Add"
        after other employees already existed, but fields were blank
    Added some comments

Version 0.3
    Added Total Amount calculation and text
    Added raise selection functionality and calculation
    Added temporary "Refresh" button until raise selection action listeneners are added
    Overtime calculation has not been added yet

Version 0.2
    Adding employees works correctly
    Fields are reset after adding
    Errors are caught and handled gracefully
    No calculation or raise calculation yet.

Version 0.1
    Working GUI
    Working sidebar - Radio Buttons
    Working topbar - labels and fields (excluding "Add" button on-click functionality)

Version Pre-Alpha:
    Construction and testing only
    
    
--Employee--

Version 1.1
    Removed an unused method, but left modifier methods in the event of future use
    Added remaining comments and getOvertime explanation/example

Version 1.0
    Added getOvertime function which allows for easy overtime calculation

Version Alpha:
    Testing, possibly functioning, awaiting PayrollCalculator progress prior to continuation

Version Pre-Alpha:
    Construction only