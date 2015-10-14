<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8" /> 
<title>Temperature Conversion</title> 

<style type="text/css"> 
 body { font: 1em calibri, arial; } 
 button { 
  background-color: transparent; 
  margin: 5px; 
  width: 300px; 
 } 
 h1, h2, h3, h4 { text-align: center; } 
 table { 
  border: 8px double; 
  margin-left: auto; 
  margin-right: auto; 
  padding: 2px; 
  height: 500px; 
  width: 30%; 
 } 
 td { border: 1px solid; } 
 td#topcell { 
  height: 200px; 
  text-align: center;  
  vertical-align: middle; 
 } 
 td#midcell { 
  height: 200px; 
  text-align: center;  
  vertical-align: middle; 
 } 
 td#bottomcell { text-align: center; } 
</style> 

</head> 
<body> 
<main role="main"> 
<table> 
 <tr> 
  <td id="topcell"> 
   <label for="Fahrenheit">Fahrenheit:</label>&nbsp;&nbsp;&nbsp;<input id="ftemp"> 
   <br /><br /> 
   <label for="Celcius">Celcius:</label>&nbsp;&nbsp;&nbsp;<input id="ctemp" readonly> 
   &nbsp;&nbsp;&nbsp;<br /><br /><br /><br /><br /><br /> 
   Fahrenheit&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Celcius                     
  </td> 
 </tr> 
 <tr> 
  <td id="midcell"> 
   <br /> 
   <textarea rows="5" id="txtArea"></textarea> 
   <p id="Fdegree">Fdegree</p> 
  </td> 
 </tr> 
 <tr> 
  <td id="bottomcell"> 
   <input type="button" value="Convert" onclick="convertTemp()" /> 
  </td> 
 </tr> 
</table> 
</main> 

<script type="text/javascript"> 
     
function convertTemp() { 
  var c = document.getElementById('ctemp'), 
      f = document.getElementById('ftemp'); 
  c.value = Math.round((f.value - 32) * 5 / 9); 

  var flist = [], clist = [], 
      tf = f.value, tc = c.value; 
  document.getElementById("Fdegree").innerHTML = f.value; 
  for (var i=0; i<220; i=i+1) { 
    tf = i; 
    tc = Math.round((tf - 32) * 5 / 9); 
    flist.push(tf);  clist.push(tc); 
  } 
  var area = document.getElementById("txtArea"); 
  var str = ""; 
  for (var i = 0; i < flist.length; i++) { 
    str += '\t' + flist[i] + '\t' + clist[i] + "\n"; 
  } 
  area.innerHTML = str; 
} 
</script> 

</body> 
</html> 