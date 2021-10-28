<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_user.jsp" flush="true" />
		
		<!-- 該当のストーリーを検索 -->
		<% String title = request.getParameter("title"); %>
		<% int uid = ((beans.UserAccountBeans) (request.getSession(false).getAttribute("user"))).getUid(); %>
		<% beans.UserStoryBeans usb = story_dao.UserStoryShowDAO.findStory(title, uid); %>
		
		<!-- eid取得 -->
		<% int eid = usb.getEid(); %>
		
		<!-- 該当の問題を検索 -->
		<% beans.UserExerciseBeans ueb = exercise_dao.UserExerciseShowDAO.findExercise(eid, uid); %>
		
		<div class="row py-3">
			<div class="col-12"><h3><%= usb.getTitle() %></h3> </div>	
		</div>
		
		
		<div class="row py-3">
			<div class="col-1"></div>
			<div class="col-10"><%= usb.getSentence() %></div>
		</div>
		
		<div class="row py-3">
			<div class="col-4"><h3>練習問題</h3></div>
		</div>
		
		<div class="row">
			<div class="col-1"></div>
			<div class="col-10"><%= ueb.getSentence() %></div>
		</div>
		
		<div class="row py-3">
			<form action="./log_in" method="post" class="col-6">
				<div class="field py-3">
					<label class="font-weight-bold">解答</label><br>
					<textarea  name="my_answer" required cols="70" rows="10" class="form-control"></textarea>
				</div>
					
				<div class="py-3">
					<input type="submit" value="解答" class="btn btn-success">
				</div>
			</form>
		</div>
		
				
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>