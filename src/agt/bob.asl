!start.

+!start
    <-  +vl(20) // by adding this belief, the NPL will trigger an obligation
        .



+!deliberate
    <-  .print("Deliberate adaptation...");
        ID = "n2";
        O = "obligation(bob,n2,apply_fine(A,X*C),false)";
        C = "sanction(A,fine(X)) & extra(C)";
        !execute(ID, O, C).

+!execute(ID, O, C)
    <-  .print("Execute adaptation: ", ID, " obligation: ", O, " consequence: ", C);
        adaptation.add_norm("n2", obligation(bob,n2,apply_fine(A,X*C),false), sanction(A,fine(X)) & extra(C));
        .print("Added new norm n2");
        //+extra(150).
        .

+unfulfilled(O) : detect
    <-  .print("Detected unfulfilled  ",O);
        .wait(2000);
        !deliberate;
        .

+active(O)
    <-  .print("Active ", O);
        +detect.

+sanction(Ag,Sanction)[norm(NormId,Event)]
    <-  .print("Sanction ",Sanction," for ",Ag," created from norm ", NormId, " that is ",Event).
