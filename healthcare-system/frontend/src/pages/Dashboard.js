import React from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Paper,
  Typography,
  Button,
  Grid,
  Card,
  CardContent,
  Box,
  AppBar,
  Toolbar
} from '@mui/material';
import CalendarTodayIcon from '@mui/icons-material/CalendarToday';
import PersonIcon from '@mui/icons-material/Person';
import NotificationsIcon from '@mui/icons-material/Notifications';

function Dashboard() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user') || '{}');

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            Healthcare Dashboard
          </Typography>
          <Typography variant="body1" sx={{ mr: 2 }}>
            {user.firstName} {user.lastName} ({user.role})
          </Typography>
          <Button color="inherit" onClick={handleLogout}>
            Logout
          </Button>
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Typography variant="h4" gutterBottom>
          Welcome, {user.firstName}!
        </Typography>

        <Grid container spacing={3} sx={{ mt: 2 }}>
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <CalendarTodayIcon sx={{ fontSize: 40, color: 'primary.main' }} />
                <Typography variant="h5" component="div">
                  Appointments
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Manage your appointments
                </Typography>
                <Button 
                  variant="contained" 
                  sx={{ mt: 2 }}
                  onClick={() => navigate('/appointments')}
                >
                  View
                </Button>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <PersonIcon sx={{ fontSize: 40, color: 'secondary.main' }} />
                <Typography variant="h5" component="div">
                  Profile
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  View and edit your profile
                </Typography>
                <Button variant="contained" sx={{ mt: 2 }}>
                  View
                </Button>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <NotificationsIcon sx={{ fontSize: 40, color: 'success.main' }} />
                <Typography variant="h5" component="div">
                  Notifications
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Check your notifications
                </Typography>
                <Button variant="contained" sx={{ mt: 2 }}>
                  View
                </Button>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
}

export default Dashboard;
