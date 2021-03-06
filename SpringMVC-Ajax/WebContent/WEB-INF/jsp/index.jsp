<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    
    <style>
	/* This stylesheet sets the width of all images to 100%: */
	img {
	  width: 100%;
	}
	</style>
    <link rel="stylesheet" href="https://bootswatch.com/readable/bootstrap.min.css" />
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  	<link rel="stylesheet" href="/resources/demos/style.css">
  	


	</head>



<body>


    <h1>${pageHeader}</h1>
    
    <table id="person-list" class="table table-striped table-hover" style="width: 35%">
        <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Surname</th>
        <th>PhoneNumber</th>
        </tr>
        
        <c:forEach items="${persons}" var="person">
            <tr>
                <td> ${person.id}</td>
               
                <td class="display${person.id}"  id="displayName${person.id}"> ${person.name}</td>
                <td class="display${person.id}"  id="displaySurname${person.id}"> ${person.surname}</td>
                <td class="display${person.id}"  id="displayPhoneNumber${person.id}"> ${person.phoneNumber}</td>
               
                <td  style="display: none;" class="edit${person.id}"> 
                  <input type="text" name="editName${person.id}" id="editName${person.id}" value="${person.name}" />
                </td>
                
                <td style="display: none;" class="edit${person.id}"> 
                  <input type="text" name="editSurname${person.id}" id="editSurname${person.id}" value="${person.surname}" />
                </td>
                
                 <td style="display: none;" class="edit${person.id}"> 
                  <input type="text" name="editPhoneNumber${person.id}" id="editPhoneNumber${person.id}" value="${person.phoneNumber}" />
                </td>
                
                <td>
                  <input type="button" class="delete-person" data-item-id="${person.id}" id="delete-person${person.id}" value="Delete" /> 
                </td>
                
                <td> 
                  <input type="button" class="update-person" data-item-id="${person.id}" id="update-person${person.id}" value="Update" /> 
                </td>
                
                <td style="display: none;" class="edit${person.id}">
                  <input type="button"  class="update-person" data-item-id="${person.id}" id="edit-person${person.id}" value="Confirm" /> 
                </td>
                
                <td id="updatecheck${person.id}" style="display: none"><span class="label label-success" >OK</span></td>
            </tr>
        </c:forEach>
  
        <tr>
            <td>NEW</td>
            <td><input type="text" name="name" id="name" placeholder="Enter Name" /></td>
            <td><input type="text" name="surname" id="surname" placeholder="Enter Surname" /></td>
            <td><input type="text" name="phoneNumber" id="phoneNumber" placeholder="Enter PhoneNumber" /></td>
            <td>
                <input type="button" id="add-person" value="Add" />
            </td>
        </tr>


	</table>
    <span class="otherchecks label label-success" style="display: none;"> SUCCESS </span>
    
    
    
<!--********************************************************-->

<!--Delete person modal-->          
<div style ="display: none;" class="deleteDialog" id="delete-dialog-form" title="Delete person">
  <p class="delete message">  Do you want to delete person!  </p>  
</div>

<!-- ******************************************************** -->

<!--Edit person modal-->          
<div style ="display: none;" class="editDialog" id="edit-dialog-form" title="Edit person details">
  <p class="validateTips">All form fields are required!</p>
  <p style="display: none;" class="error_place_class">Form fields cannot be empty!</p>
  
  <form>
    <fieldset>
      
      <label for="name">Name*</label>
      <input type="text" name="name" id="edit_name" value="" class="text ui-widget-content ui-corner-all">
      <label for="surname">Surname*</label>
      <input type="text" name="surname" id="edit_surname" value="" class="text ui-widget-content ui-corner-all">
      <label for="phoneNumber">PhoneNumber*</label>
      <input type="text" name="phoneNumber" id="edit_phoneNumber" value="" class="text ui-widget-content ui-corner-all">

      <input type="submit" value="Edit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>
<!--********************************************************-->


<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="../lib/jquery-input-mask-phone-number.js"></script> 

<script>

    $(document).ready(function() {
      
        $("#add-person").click(function(){
            
            var name= $("#name").val();
            var surname = $("#surname").val();
            var phoneNumber = $("#phoneNumber").val();
           
            var data = JSON.stringify({"name":name,"surname":surname,"phoneNumber":phoneNumber});
           
            $.ajax({
            type : "POST",
            url : "${pageContext.request.contextPath}/addPerson",
            contentType: "application/json",
            data : data,
            success: function(data){
            
            if(data===""){
                return;
            }else{           
                var html = [];
                html.push(
                  '<tr>',
                  '<td>'+data.id+'</td>',
                  
                  '<td class="display'+data.id+'" id="displayName'+data.id+'">'+data.name+'</td>',
                  '<td class="display'+data.id+'" id="displaySurname'+data.id+'">'+ data.surname+'</td>',
                  '<td class="display'+data.id+'" id="displayPhoneNumber'+data.id+'">'+ data.phoneNumber+'</td>',
                  
                  '<td style="display: none;" class="edit'+data.id+'"> <input type="text" name="editName'+data.id+'" id="editName'+data.id+'" value="'+data.name+'" /></td>',
                  '<td style="display: none;" class="edit'+data.id+'"> <input type="text" name="editSurname'+data.id+'" id="editSurname'+data.id+'" value="'+data.surname+'" /></td>',
                  '<td style="display: none;" class="edit'+data.id+'"> <input type="text" name="editPhoneNumber'+data.id+'" id="editPhoneNumber'+data.id+'" value="'+data.phoneNumber+'" /></td>',
                  
                  '<td><input type="button" class="delete-person" data-item-id="'+data.id+'" id="delete-person'+data.id+'" value="Delete" /></td>',
                  '<td> <input type="button" class="update-person" data-item-id="'+data.id+'" id="update-person'+data.id+'" value="Update" /> </td>',
                  
                  '<td style="display: none;" class="edit'+data.id+'"> <input type="button"  class="update-person" data-item-id="'+data.id+'" id="edit-person'+data.id+'" value="Confirm" /> </td>',
                  
                  '<td id="updatecheck'+data.id+'" style="display: none;background-color: greenyellow; color:white;">OK</td>',
                  '</tr>'
                );
                var a = html.join("");
                $('#person-list tr:last').before(a);
                   $("#name").val("");
                   $("#surname").val("");
                   $("#phoneNumber").val("");
                   $(".otherchecks").show();
                   setTimeout(function() { $(".otherchecks").hide(); }, 2000);
                 }
               }
            });
        });
        
        $("#person-list").on('click','.update-person',function(){
        	 
            var value = $(event.target).val();
            var id = $(event.target).data('itemId');

            var tdclasshide = ".display"+id;
            var editnameid = "#editName"+id;
            var editsurnameid = "#editSurname"+id;
            var editphoneNumberid = "#editPhoneNumber"+id;
         
            var gelen_name 	=  $(editnameid).val();
            var gelen_surname	=  $(editsurnameid).val();
            var gelen_phoneNumber	=  $(editphoneNumberid).val();
            
            $('#edit_name').val( gelen_name );
	      	$('#edit_surname').val( gelen_surname );
	      	$('#edit_phoneNumber').val( gelen_phoneNumber );
			            

            
        	$( "#edit-dialog-form" ).dialog({

      	      height: 300,
      	      width: 1000,
      	      modal: true,
	      
	      	    buttons: {
	      	        
	      	    	OKKK : function() {
	    		    	  
	 		    	 var edited_name = $('#edit_name').val();
	 		    	 var edited_surname =  $('#edit_surname').val();
	 		    	 var edited_phoneNumber =  $('#edit_phoneNumber').val();
	 		    	
	 		    	if(edited_name.length == 0 || edited_surname.length == 0 || edited_phoneNumber.length == 0){
	 		    		 $(".error_place_class").show();
	 		    	 }else{
														
			                var data = JSON.stringify({"id":id, "name":edited_name, "surname":edited_surname, "phoneNumber":edited_phoneNumber});
			                $.ajax({
			                   type : "PUT",
			                   url : "${pageContext.request.contextPath}/updatePerson",
			                   contentType: "application/json",
			                   data : data,
			                   success: function(data){
			                     $(tdclasshide).show();
			                     
			                     var dispName = "#displayName"+id;
			                     var dispSurname = "#displaySurname"+id;
			                     var dispPhoneNumber = "#displayPhoneNumber"+id;
			                     
			                     $(dispName).text(edited_name);
			                     $(dispSurname).text(edited_surname);
			                     $(dispPhoneNumber).text(edited_phoneNumber);
			                     
			                     var edit_Name = "#editName"+id;
			                     var edit_Surname = "#editSurname"+id;
			                     var edit_PhoneNumber = "#editPhoneNumber"+id;
			                     
			                     $(edit_Name).text(edited_name);
			                     $(edit_Surname).text(edited_surname);
			                     $(edit_PhoneNumber).text(edited_phoneNumber);
			                     
			                     $('#updatecheck'+id).show();
			                     setTimeout(function() { $('#updatecheck'+id).hide(); }, 2000);
			                   }
			                });
				 	 		         $( this ).dialog( "close" );
				 		   }
			 		 },
	      	        
	 		      	Cancel: function() {
	      	          $( this ).dialog( "close" );
	      	        }
	      	      }
      		    
      		});
           
        });
        $("#person-list").on('click','.delete-person',function(event){
            var id = $(event.target).data('itemId');
            var data = JSON.stringify({"id":id});
            var rowId= "#"+event.target.id;
            
            
        	$( "#delete-dialog-form" ).dialog({
    	      height: 300,
    	      width: 1000,
    	      modal: true,
    	      
	      	    buttons: {
	      	        
	      	    	OKKK : function() {

	      	            $.ajax({
	      	              type : "DELETE",
	      	              url : "${pageContext.request.contextPath}/deletePerson",
	      	              contentType: "application/json",
	      	              data : data,
	      	              success: function(data){
	      	                 $(rowId).closest('tr').remove();
	      	                 $(".otherchecks").show();
	      	                 setTimeout(function() { $(".otherchecks").hide(); }, 2000);
	      	              	}
	      	              });
	      	            
	      	          $( this ).dialog( "close" );
	 		      	},
	      	        
	 		      	Cancel: function() {
	      	          $( this ).dialog( "close" );
	      	        }
	      	      }
    		    
    		});
            
        });
        
        $("#testmap").click(function(){
           
           var data = JSON.stringify({question:"1",answer:"2"});
          
            $.ajax({
            type : "POST",
            url : "${pageContext.request.contextPath}/testmap",
            headers: { 
                'Accept': 'application/json',
                'Content-Type': 'application/json' 
            },
            data : data,
            success: function(data){
               
            }
            });
        });
        
        
    });
    
</script>
</body>
</html>