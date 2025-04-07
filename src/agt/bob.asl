!start.

+!start
    <-  +vl(20) // by adding this belief, the NPL will trigger an obligation
        .

+detect
    <-  .print("Detecting ...");
        .

+!adapt
    <-  .print("Adapt norms...");
        adaptation.add_norm("n2", obligation(bob,n2,apply_fine(A,X*C),false), sanction(A,fine(X)) & extra(C));
        .print("Added new norm n2");
        +extra(150).

+unfulfilled(O)
    <-  .print("Unfulfilled ",O);
        .wait(2000);
        !adapt;
        .

+active(O)
    <-  .print("Active ", O);
        !detect.

+sanction(Ag,Sanction)[norm(NormId,Event)]
    <-  .print("Sanction ",Sanction," for ",Ag," created from norm ", NormId, " that is ",Event).
