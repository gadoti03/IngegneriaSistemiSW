%====================================================================================
% fireflysync description   
%====================================================================================
event( syncronize, syncronize(TIME,FREQ) ).
dispatch( cellstate, cellstate(X,Y,COLOR) ).
%====================================================================================
context(ctxfireflies, "localhost",  "TCP", "8040").
context(ctxgrid, "127.0.0.1",  "TCP", "8050").
 qactor( griddisplay, ctxgrid, "external").
  qactor( creator, ctxfireflies, "it.unibo.creator.Creator").
 static(creator).
  qactor( firefly, ctxfireflies, "it.unibo.firefly.Firefly").
dynamic(firefly). %%Oct2023 
