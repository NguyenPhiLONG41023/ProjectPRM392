USE [master]
GO
/****** Object:  Database [ProjectPRM]    Script Date: 9/20/2024 11:09:32 AM ******/
CREATE DATABASE [ProjectPRM]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ProjectPRM', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ProjectPRM.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'ProjectPRM_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ProjectPRM_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [ProjectPRM] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ProjectPRM].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ProjectPRM] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ProjectPRM] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ProjectPRM] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ProjectPRM] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ProjectPRM] SET ARITHABORT OFF 
GO
ALTER DATABASE [ProjectPRM] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ProjectPRM] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ProjectPRM] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ProjectPRM] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ProjectPRM] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ProjectPRM] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ProjectPRM] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ProjectPRM] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ProjectPRM] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ProjectPRM] SET  ENABLE_BROKER 
GO
ALTER DATABASE [ProjectPRM] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ProjectPRM] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ProjectPRM] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ProjectPRM] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ProjectPRM] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ProjectPRM] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ProjectPRM] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ProjectPRM] SET RECOVERY FULL 
GO
ALTER DATABASE [ProjectPRM] SET  MULTI_USER 
GO
ALTER DATABASE [ProjectPRM] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ProjectPRM] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ProjectPRM] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ProjectPRM] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [ProjectPRM] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [ProjectPRM] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'ProjectPRM', N'ON'
GO
ALTER DATABASE [ProjectPRM] SET QUERY_STORE = OFF
GO
USE [ProjectPRM]
GO
/****** Object:  Table [dbo].[Brands]    Script Date: 9/20/2024 11:09:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Brands](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cart]    Script Date: 9/20/2024 11:09:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cart](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[User_Id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CartItems]    Script Date: 9/20/2024 11:09:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CartItems](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Cart_Id] [int] NULL,
	[Product_Id] [int] NULL,
	[Quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 9/20/2024 11:09:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[Order_Id] [int] NOT NULL,
	[Product_Id] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Price] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Order_Id] ASC,
	[Product_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 9/20/2024 11:09:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[User_Id] [int] NULL,
	[OrderDate] [datetime] NULL,
	[TotalPrice] [decimal](10, 2) NOT NULL,
	[Status] [nvarchar](50) NULL,
	[PaymentMethod] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 9/20/2024 11:09:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
	[Quantity] [int] NOT NULL,
	[Description] [nvarchar](255) NULL,
	[Price] [decimal](10, 2) NOT NULL,
	[Size] [nvarchar](50) NULL,
	[Brand_Id] [int] NULL,
	[Image] [nvarchar](255) NULL,
	[Status] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 9/20/2024 11:09:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Role_Name] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 9/20/2024 11:09:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](255) NOT NULL,
	[Email] [nvarchar](100) NOT NULL,
	[PhoneNumber] [nvarchar](15) NULL,
	[Address] [nvarchar](255) NULL,
	[RoleID] [int] NOT NULL,
	[Status] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Brands] ON 

INSERT [dbo].[Brands] ([Id], [Name]) VALUES (1, N'Adidas')
INSERT [dbo].[Brands] ([Id], [Name]) VALUES (2, N'Nike')
INSERT [dbo].[Brands] ([Id], [Name]) VALUES (3, N'Fila')
INSERT [dbo].[Brands] ([Id], [Name]) VALUES (4, N'Puma')
SET IDENTITY_INSERT [dbo].[Brands] OFF
GO
SET IDENTITY_INSERT [dbo].[Cart] ON 

INSERT [dbo].[Cart] ([Id], [User_Id]) VALUES (1, 2)
INSERT [dbo].[Cart] ([Id], [User_Id]) VALUES (2, 3)
SET IDENTITY_INSERT [dbo].[Cart] OFF
GO
SET IDENTITY_INSERT [dbo].[CartItems] ON 

INSERT [dbo].[CartItems] ([Id], [Cart_Id], [Product_Id], [Quantity]) VALUES (1, 1, 1, 2)
INSERT [dbo].[CartItems] ([Id], [Cart_Id], [Product_Id], [Quantity]) VALUES (2, 1, 4, 1)
INSERT [dbo].[CartItems] ([Id], [Cart_Id], [Product_Id], [Quantity]) VALUES (3, 2, 2, 1)
INSERT [dbo].[CartItems] ([Id], [Cart_Id], [Product_Id], [Quantity]) VALUES (4, 2, 5, 1)
SET IDENTITY_INSERT [dbo].[CartItems] OFF
GO
INSERT [dbo].[OrderDetails] ([Order_Id], [Product_Id], [Quantity], [Price]) VALUES (1, 1, 2, CAST(350000.00 AS Decimal(10, 2)))
INSERT [dbo].[OrderDetails] ([Order_Id], [Product_Id], [Quantity], [Price]) VALUES (1, 4, 1, CAST(400000.00 AS Decimal(10, 2)))
INSERT [dbo].[OrderDetails] ([Order_Id], [Product_Id], [Quantity], [Price]) VALUES (2, 7, 1, CAST(700000.00 AS Decimal(10, 2)))
GO
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([Id], [User_Id], [OrderDate], [TotalPrice], [Status], [PaymentMethod]) VALUES (1, 2, CAST(N'2024-09-20T11:08:09.230' AS DateTime), CAST(1050000.00 AS Decimal(10, 2)), N'Pending', 1)
INSERT [dbo].[Orders] ([Id], [User_Id], [OrderDate], [TotalPrice], [Status], [PaymentMethod]) VALUES (2, 3, CAST(N'2024-09-20T11:08:09.230' AS DateTime), CAST(750000.00 AS Decimal(10, 2)), N'Pending', 2)
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
SET IDENTITY_INSERT [dbo].[Products] ON 

INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (1, N'Áo thun Adidas S', 50, N'Áo thun nam Adidas ch?t li?u cotton', CAST(350000.00 AS Decimal(10, 2)), N'S', 1, N'adidas_tshirt_s.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (2, N'Áo thun Adidas M', 50, N'Áo thun nam Adidas ch?t li?u cotton', CAST(350000.00 AS Decimal(10, 2)), N'M', 1, N'adidas_tshirt_m.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (3, N'Áo thun Adidas L', 50, N'Áo thun nam Adidas ch?t li?u cotton', CAST(350000.00 AS Decimal(10, 2)), N'L', 1, N'adidas_tshirt_l.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (4, N'Áo thun Nike S', 50, N'Áo thun nam Nike ch?t li?u cotton', CAST(400000.00 AS Decimal(10, 2)), N'S', 2, N'nike_tshirt_s.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (5, N'Áo thun Nike M', 50, N'Áo thun nam Nike ch?t li?u cotton', CAST(400000.00 AS Decimal(10, 2)), N'M', 2, N'nike_tshirt_m.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (6, N'Áo thun Nike L', 50, N'Áo thun nam Nike ch?t li?u cotton', CAST(400000.00 AS Decimal(10, 2)), N'L', 2, N'nike_tshirt_l.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (7, N'Áo khoác Fila M', 30, N'Áo khoác nam Fila ch?t li?u ch?ng nu?c', CAST(700000.00 AS Decimal(10, 2)), N'M', 3, N'fila_jacket_m.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (8, N'Áo khoác Fila L', 30, N'Áo khoác nam Fila ch?t li?u ch?ng nu?c', CAST(700000.00 AS Decimal(10, 2)), N'L', 3, N'fila_jacket_l.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (9, N'Áo khoác Puma S', 20, N'Áo khoác nam Puma ch?t li?u nh?', CAST(750000.00 AS Decimal(10, 2)), N'S', 4, N'puma_jacket_s.jpg', 1)
INSERT [dbo].[Products] ([Id], [Name], [Quantity], [Description], [Price], [Size], [Brand_Id], [Image], [Status]) VALUES (10, N'Áo khoác Puma M', 20, N'Áo khoác nam Puma ch?t li?u nh?', CAST(750000.00 AS Decimal(10, 2)), N'M', 4, N'puma_jacket_m.jpg', 1)
SET IDENTITY_INSERT [dbo].[Products] OFF
GO
SET IDENTITY_INSERT [dbo].[Roles] ON 

INSERT [dbo].[Roles] ([Id], [Role_Name]) VALUES (1, N'Admin')
INSERT [dbo].[Roles] ([Id], [Role_Name]) VALUES (2, N'User')
SET IDENTITY_INSERT [dbo].[Roles] OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([Id], [Username], [Password], [Email], [PhoneNumber], [Address], [RoleID], [Status]) VALUES (1, N'admin', N'123456', N'admin@example.com', N'0123456789', N'123 Admin St', 1, 1)
INSERT [dbo].[Users] ([Id], [Username], [Password], [Email], [PhoneNumber], [Address], [RoleID], [Status]) VALUES (2, N'user1', N'123456', N'user1@example.com', N'0987654321', N'456 User St', 2, 1)
INSERT [dbo].[Users] ([Id], [Username], [Password], [Email], [PhoneNumber], [Address], [RoleID], [Status]) VALUES (3, N'user2', N'123456', N'user2@example.com', N'0912345678', N'789 User St', 2, 1)
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT (getdate()) FOR [OrderDate]
GO
ALTER TABLE [dbo].[Orders] ADD  DEFAULT ('Pending') FOR [Status]
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD FOREIGN KEY([User_Id])
REFERENCES [dbo].[Users] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CartItems]  WITH CHECK ADD FOREIGN KEY([Cart_Id])
REFERENCES [dbo].[Cart] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CartItems]  WITH CHECK ADD FOREIGN KEY([Product_Id])
REFERENCES [dbo].[Products] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD FOREIGN KEY([Order_Id])
REFERENCES [dbo].[Orders] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD FOREIGN KEY([Product_Id])
REFERENCES [dbo].[Products] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([User_Id])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD FOREIGN KEY([Brand_Id])
REFERENCES [dbo].[Brands] ([Id])
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD FOREIGN KEY([RoleID])
REFERENCES [dbo].[Roles] ([Id])
GO
USE [master]
GO
ALTER DATABASE [ProjectPRM] SET  READ_WRITE 
GO
