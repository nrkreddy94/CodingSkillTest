$(document).ready(function() {
		setInterval(startTime, 1000); // update time every section
		enableDragable(); // enable draggable feature
		
		// enable draggable feature on click on parent div or inner div
		$("#draggable, #dragme").click(function(){
			enableDragable();
		})
	
		
		/* Position radio button check action*/
		$("input[name='dragme-position']").click(function(){
            let radioValue = $("input[name='dragme-position']:checked").val();
            let labelText=$("input[name='dragme-position']:checked").next().text();
            if(radioValue){
            	disableDragable();
            	$("#postion").html(labelText) 
            	$("#dragme").removeAttr('class');
            	$("#dragme").attr('class', radioValue);            	
            }
        });
		
		   
	});
	$(document).keyup(function(e) {
		if (e.which == 27) hideDragable();   // esc key pressed
		if (e.which == 13) showDragable();     // enter key pressed
		  
	});
	const enableDragable=()=> $("#dragme").draggable( {scroll: false,containment: "parent",disabled: false }); // enable draggable feature
	const disableDragable=()=> $("#dragme").draggable( {scroll: false,containment: "parent",disabled: true }); // disable draggable feature
	const hideDragable=()=> $("#dragme").hide();  // hide inner div dragme
	const showDragable=()=> $("#dragme").show();// show inner div dragme
	
	/* Timer function*/
	const startTime=()=> {
		const today = new Date();
		let h = today.getHours();
		let m = today.getMinutes();
		let s = today.getSeconds();
		m = checkTime(m);
		s = checkTime(s);
		$("#clock").html(h + ":" + m + ":" + s)

	}
	const checkTime =(i)=> {
		// add zero in front of numbers < 10
		if (i < 10) 
			i = "0" + i
		return i;
	}