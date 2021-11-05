<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_user.jsp" flush="true" />
		
		<!-- 該当の問題を検索 -->
		<% int eid = Integer.parseInt(request.getParameter("eid")); %>
		<% int uid = ((beans.UserAccountBeans) request.getSession(false).getAttribute("user")).getUid(); %>
		<% beans.UserExerciseBeans ueb = exercise_dao.UserExerciseShowDAO.findExercise(eid, uid); %>
		
		<div class="row py-3">
			<div class="col-4"><h3>問題</h3> </div>
			<div class="col-7"></div>
			<div class="col-1">
				<% if (ueb.getIsBookmarked()) { %>
		            	<a href="/LearnSqlServlet/users/exercise/bookmark?eid=<%= ueb.getEid() %>&uid=<%= uid %>">
		            		<span class="text-primary"><i class="fas fa-bookmark"></i></span>
		           		</a>
		           	<% } else if (!ueb.getIsBookmarked()){ %>
		           		<a href="/LearnSqlServlet/users/exercise/bookmark?eid=<%= ueb.getEid() %>&uid=<%= uid %>">
		           			<span class="text-primary"><i class="far fa-bookmark"></i></span>
		           		</a>
		           	<% } else {%>
		           		<span class="text-secondary"><i class="fas fa-question-circle"></i></span>
		           	<% } %>
			</div>
		</div>
		
		<div class="row py-3">
			<div class="col-6"><%= ueb.getSentence() %></div>
			<div class="col-6">
				<% if (ueb.getChallengeDate() == null) { %>
					<p>まだ挑戦したことのない問題です！</p>
					<p>チャレンジしてみましょう</p>
				<% } else { %>
					<table class="table">
						<tr>
							<th>最終挑戦日時</th>
							<td><%= ueb.getChallengeDate() %></td>
						</tr>
						
						<tr>
							<th>前回の正誤</th>
							<td>
								<% if (ueb.getIsCorrect()) {  %>
									正解！
								<% } else { %>
									不正解
								<% } %>
							</td>
						</tr>
						
						<tr>
							<th>前回の解答</th>
							<td><%= ueb.getMyAnswer() %></td>
						</tr>
					</table>
				<% } %>
			</div>
		</div>
		
		<div class="row py-3">
			<form action="./show?eid=<%= eid %>" method="post" class="col-6">
				<div class="field py-3">
					<label class="font-weight-bold">解答</label><br>
					
						<% java.util.Map<String, Object> result_map = (java.util.HashMap<String, Object>) request.getSession(false).getAttribute("result_map"); %>
						
						<% if(result_map != null) { %>
							<textarea  name="my_answer" required cols="70" rows="10" class="form-control"><%= result_map.get("my_answer")  %></textarea>
						<% } else { %>
							<textarea  name="my_answer" required cols="70" rows="10" class="form-control"></textarea>
						<% } %>
				</div>
					
				<div class="py-3">
					<input type="submit" value="解答" class="btn btn-success">
				</div>
			</form>
			<div class="col-5 py-3">
				<label class="font-weight-bold">実行結果</label><br>
				<div>
					<% if (result_map != null) { %>
						<% if (result_map.get("result") instanceof String) { %>
							<span class="text-danger">
								<%= result_map.get("result") %>
							</span>
						<% } else if (result_map.get("result") instanceof java.util.ArrayList) { %>
							<% java.util.ArrayList<java.util.ArrayList<String>> result_array = (java.util.ArrayList<java.util.ArrayList<String>>) result_map.get("result"); %>
							<table class="table table-bordered">
								<tr>
									<% for(int i = 0; i < result_array.get(0).size(); i++) { %>
										<th class="bg-light"><%= result_array.get(0).get(i) %></th>
									<% } %>
								</tr>
								
								<% for(int i = 1; i < result_array.size(); i++) { %>
									<tr>
										<% for(int j = 0; j < result_array.get(i).size(); j++) { %>
											<td><%= result_array.get(i).get(j) %></td>
										<% } %>
									</tr>
								<% } %>
								
							</table>
						<% } %>
					<% } else {%>
						<% System.out.println("result_map = " + result_map); %>
					<% } %>
				</div>
			</div>
		</div>
		
		
		
		<div class="row py-3">
			<div class="col-4"><h3>今までの解答履歴</h3> </div>
		</div>
		
		<table class="table">
			<tr class="bg-light">
				<th>前回の結果</th>
				<th>その時の解答</th>
				<th>挑戦日時</th>
			</tr>
			
			<% java.util.List<java.util.List<String>> logs = exercise_dao.UserAnswerLogDAO.findAnswerLog(ueb.getEid(), uid); %>
			<% for(int i = 0; i < logs.size(); i++) { %>
				<tr>
					<% java.util.List<String> log = logs.get(i); %>
					<td>
						<% if (Boolean.parseBoolean(log.get(0))) { %>
							<span class="text-success"><i class="fas fa-check-circle"></i></span>　正解
						<% } else { %>
							<span class="text-danger"><i class="fas fa-times-circle"></i></span>　不正解
						<% } %>
					</td>
					<td><%= log.get(1) %></td>
					
					<td><%= log.get(2) %></td>
				</tr>
			<% } %>
		</table>
		
		<% if (request.getSession(false).getAttribute("result_map") != null) {
				request.getSession(false).removeAttribute("result_map");
			} %>
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>