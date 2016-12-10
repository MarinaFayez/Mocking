import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class Utest {
	
	private static final int Test = 0;
	@Mock 
	Connection conn;
	@Mock
	PreparedStatement psmt;
	@InjectMocks
	DAOImpl newDAO = new DAOImpl();
	@InjectMocks
		Product product = new Product(25);
	// A method that runs before each test method
	

	@Test
	public void testConstructor() {
	    assertEquals(25, product.getId());
	   // assertTrue(product.getType().equals("Ahmed"));
	    
	}
	
	@Test
	public void testTypeSetter() {
	   product.setType("device");
	   assertTrue(product.getType().equals("device"));    
	}
	
	@Test
	public void testManufacturerSetter() {
	   product.setManufacturer("chinese");
	   assertTrue(product.getManufacturer().equals("chinese"));    
	}	
	
	@Test
	public void testProductionDateSetter() {
	   product.setProductionDate("12/12/2012");
	   assertTrue(product.getProductionDate().equals("12/12/2012"));    
	}
	
	@Test
	public void testExpiryDateSetter() {
	   product.setExpiryDate("12/12/2017");
	   assertTrue(product.getExpiryDate().equals("12/12/2017"));    
	}

	@Test (expected = DAOException.class)
	public void ExceptionCase() throws SQLException, DAOException{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		when(psmt.executeUpdate()).thenThrow(new SQLException());
		newDAO.updateProduct(product);
	}
	
	@Test
	public void HappyTest1() throws SQLException, DAOException
	{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		//when(psmt.setString(anyInt(), anyString())).thenReturn(1);
		when(psmt.executeUpdate()).thenReturn(1);
		Product p = new Product(22);
		newDAO.updateProduct(p);
		}
	
	@Test
	public void HappyTest2() throws SQLException, DAOException{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		when(psmt.executeUpdate()).thenReturn(1);

		product.setType("device");
		product.setManufacturer("chinese");
		product.setProductionDate("12/12/2012");
		product.setExpiryDate("12/12/2017");

		
		
		newDAO.updateProduct(product);
		verify(psmt, times(1)).executeUpdate();
		ArgumentCaptor<Integer> stringcaptor = ArgumentCaptor.forClass(Integer.class);
		verify(psmt, times(1)).setInt(anyInt(), stringcaptor.capture());
		Assert.assertTrue(stringcaptor.getAllValues().get(0).equals(25));
		
		ArgumentCaptor<String> Stringcaptor = ArgumentCaptor.forClass(String.class);
		verify(psmt, times(4)).setString(anyInt(), Stringcaptor.capture());
		
		Assert.assertTrue(Stringcaptor.getAllValues().get(0).equals("device"));
		Assert.assertTrue(Stringcaptor.getAllValues().get(1).equals("chinese"));
		//Assert.assertTrue(stringcaptor.getAllValues().get(2).equals("12/12/2012"));
		//Assert.assertTrue(stringcaptor.getAllValues().get(3).equals("12/12/2017"));
	}
	
	
	@Test 
	public void HappyTestDelete() throws SQLException,DAOException
	{
		when(conn.prepareStatement(anyString())).thenReturn(psmt);
		when(psmt.executeUpdate()).thenReturn(1);
		newDAO.deleteProduct(product.getId());
	}

	
}
